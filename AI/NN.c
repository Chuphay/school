#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define BYTE 8
#define SIZE 3*BYTE
#define OUT 8
#define IN BYTE + 16


void print_byte(char a){
  int i;
  for(i = 0; i < BYTE; i++) {
      printf("%d", !!((a << i) & 128));
  }
  printf("\n");
}

void print_theta(double ** theta){

  printf("theta: \n");
  int m,n;
  for(m = 0; m < BYTE; m++){
    for(n = 0; n< SIZE; n++){
      printf("%0.2f ", theta[m][n]);
    }
    printf("\n");
  } 
}
 

double sigmoid(double z){
  if(z>4) return 1;
  if(z<-4) return 0;

  double denom = 1 + exp(-z);
  return 1.0/denom;
} 

double dot(double *a, double *b){
  double out = 0;
  int i;
  for(i = 0; i < SIZE; i++){
    out += a[i]*b[i];
  }
  return out;
} 
/*
int index(int x, int y, int z) {
  return x + (y*xSize) + (z*ySize*xSize);
}

int value = array[index(a, b, c)];
*/


char activate_neuron(char a, char b, char c, double **theta){
  //char a is simply the control bit
  //it's for future flexibility
  //and can be set to anything... for now
  //we set it to to 128 so that its binary representation is
  //10000000
  //and we use this as something like
  //an electrical ground against the inputs

  //char b and char c are the inputs

  //x and y describe the array theta
  //with x characterizing the output
  //and y characterizing the input

  a = a|128;

  double input[SIZE];

  int i;
  for(i = 0; i < BYTE; i++){
    input[i] = !!((a << i) & 128);
    input[i+BYTE] = !!((b << i) & 128);
    input[i+2*BYTE] = !!((c << i) & 128);
  }

  char out = 0;
  for (i = 0 ; i< BYTE; i++){
    double temp;  
    temp = sigmoid(dot(input, theta[i]));

    if(temp>0.5){
      out = out|(1<<(BYTE-i-1));
    }
  }

  return out ;
}


typedef struct thet{
  double **t;
} thet;

typedef struct net{
  //look to make_net for explanation
  int length;
  int size;
  int *layers;
  thet *theta;
  char name[16];
} net;


double **make_theta(){
  double **out = malloc(BYTE*sizeof(double *));
  int i,j;
  for(i = 0; i < BYTE; i++){
    out[i] = malloc(SIZE*sizeof(double));
    for(j = 0; j < SIZE; j++){
      out[i][j] = rand()/((double)RAND_MAX) -0.5;
    }
  }
  return out;
}

double **make_or(){
  double **out = malloc(BYTE*sizeof(double *));
  int i;
  for(i = 0; i < BYTE; i++){
    out[i] = malloc(SIZE*sizeof(double));
    
    out[i][0] = -20;
    out[i][BYTE+i] = 30;
    out[i][2*BYTE + i] = 30;
    
  }
  return out;
}

double **make_and(){
  double **out = malloc(BYTE*sizeof(double *));
  int i;
  for(i = 0; i < BYTE; i++){
    out[i] = malloc(SIZE*sizeof(double));
    
    out[i][0] = -30;
    out[i][BYTE+i] = 20;
    out[i][2*BYTE + i] = 20;
    
  }
  return out;
}

double **make_not(){
  double **out = malloc(BYTE*sizeof(double *));
  int i;
  for(i = 0; i < BYTE; i++){
    out[i] = malloc(SIZE*sizeof(double));
    
    out[i][0] = 10;
    out[i][BYTE+i] = -30;
    //out[i][2*BYTE + i] = 0;
    
  }
  return out;
}



net *make_net(int size, int length, int *layers){
  //for example if the layers are like 2x2x1
  //then the size would be 5
  //truthfully length (or size) is reduntant,
  //but it just seems easier to pass this extra information
  int i;
  //the reason we are subtracting layers[0]
  //is because 2X2X1 is two inputs, two hidden neurons, and one neuron to sum
  //therefor there are really only 3 neurons
  thet *theta = malloc((size-layers[0])*sizeof(thet *));
  for( i = 0 ; i< (size-layers[0]); i++){
    //printf("make i: %d\n",i);
    //had to add in this awkward t thing
    //still not sure how to reference a 2-d matrix in an array
    theta[i].t = make_theta();
    //print_theta(theta[i].t);
  }

  net *out = malloc(sizeof(net));

  out->size = size;
  out->layers = layers;
 out->theta = theta;
 out->length = length;
 return out;
} 


void free_net(net *x){
  printf("claning up....?\n");

}

char activate_net(char a, char b, char c, net *x){

  int i;
  // double **or = make_or();
  //double **and = make_and();
  //double **not = make_not();
  for(i = 0 ; i < x->length -2;i++){
    //printf("i: %d\n",i);
    //print_theta(x->theta[2*i].t);
    char b_new = activate_neuron(a,b,c,x->theta[2*i].t);
    c = activate_neuron(a,b,c,x->theta[2*i+1].t);
    b = b_new;

    //from here how does one solve this problem?
    //it's not so easy at all
    //I could easily calculate and then save somewhere
    //and then call back
    //or I could do a recursive algorithm
    //but with the comments below
    //the straightforward approach seems best.
}
  char out;

  out = activate_neuron(a,b,c,x->theta[(x->size)-3].t);
  //here we subtract 3 because 2 from the input and one for
  //normal off by one conversion




  //here's what I'm doing from now on
  //Im going to assume that the net is of the form
  //2X2X2...2X2X1
  //this is C
  //and I'm no expert. 
  //And most importantly,
  //Let's make sure we can finish this project!!
  return out;
}

void train(char a, char b, char c, char out, net *x, int epochs){
  printf("inside\n");
  double storage[x->size - 2][BYTE];
  //temp is here to hold the intermediate values


  //so i have to repeat my activate-neuron code
  //because there I was simply returning zeros and ones
  //but apparently to use the backpropagation algorithm
  //I actually need floating point numbers
  a = a|128;

  double input[SIZE];

  int i,n;


    for(n = 0; n < BYTE; n++){
      input[n] = !!((a << n) & 128);
      input[n+BYTE] = !!((b << n) & 128);
      input[n+2*BYTE] = !!((c << n) & 128);

  for(i = 0 ; i < x->length - 2;i++){
   
    char b_new = activate_neuron(a,b,c,x->theta[2*i].t);
    c = activate_neuron(a,b,c,x->theta[2*i+1].t);
    b = b_new;
    storage[2*i][n] = sigmoid(dot(input,x->theta[2*i+1].t[n]));;
    storage[2*i+1][n] = sigmoid(dot(input, x->theta[2*i+1].t[n]));

}


  storage[x->size-3][n] =  sigmoid(dot(input, x->theta[x->size -3].t[n]));

    }
  printf("%f\n",storage[x->size-3][0]);
}



int main(){

  srand(time(NULL));

  int layers[4] = {2,2,2,1};
  net *try = make_net(7,4,layers);
  print_byte(activate_net(0,12,75,try));
  print_byte(12^75);
  train(0,12,75,12^75,try,10);


  free_net(try);

  return 0;
}
