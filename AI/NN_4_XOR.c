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

double dot(double *a, double *b, int length){
  double out = 0;
  int i;
  for(i = 0; i < length; i++){
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
    temp = sigmoid(dot(input, theta[i], SIZE));

    if(temp>0.5){
      out = out|(1<<(BYTE-i-1));
    }
  }

  return out ;
}

typedef struct net{
  //look to make_net for explanation
  int length;
  int size;
  int *layers;
  double *theta;
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
  double *theta = malloc((size-layers[0])*sizeof(double **));
  for( i = 0 ; i< (size-layers[0]); i++){
    theta[i] = **make_theta();
  }

  net *out = malloc(sizeof(net));

  out->size = size;
  out->layers = layers;
 out->theta = theta;
 out->length = length;
 return out;
} 


void free_net(net *x){
  printf("here\n");

}

char activate_net(char a, char b, char c, net *x){
  printf("activating the net\n");
  printf("%d\n", x->size);
  print_byte(b);
  print_byte(c);

  int i;
  double **or = make_or();
 double **and = make_and();
 //print_theta(and);
 double **not = make_not();
  for(i = x->length-2 ; i>0 ;i--){
    printf("layer: %d\n",i);
    printf("length:  %d\n", x->layers[i]);

    char b_new = activate_neuron(a,b,c,or);
    c = activate_neuron(a,b,c,and);
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

  printf("wow\n");
  out = activate_neuron(a,b,activate_neuron(a,c,a,not),and);

  //here's what I'm doing from now on
  //Im going to assume that the net is of the form
  //2X2X2...2X2X1
  //this is C
  //and I'm no expert. 
  //And most importantly,
  //Let's make sure we can finish this project!!
  return out;
}


int main(){

srand(time(NULL));

  int layers[3] = {2,2,1};
  net *try = make_net(5,3,layers);
  print_byte(activate_net(0,12,75,try));
  print_byte(12^75);
  print_byte(219|31);

  //double **this = make_theta();
  double **or = make_or();

  print_byte(activate_neuron(1,12,75,or));
  double **and = make_and();
  // print_theta(and);
  print_byte(activate_neuron(0,12,75,and));
  print_byte(12&75);

  free_net(try);

  return 0;
}
