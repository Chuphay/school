#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define BYTE 8
#define SIZE 8+2*BYTE
#define OUT 8
#define IN BYTE + 16


void print_byte(char a){
  int i;
  for(i = 0; i < BYTE; i++) {
      printf("%d", !!((a << i) & 128));
  }
  printf("\n");
}

void print_theta(double ** theta, int in, int out){

  printf("theta: \n");
  int m,n;
  for(m = 0; m < out; m++){
    for(n = 0; n< in; n++){
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


char neuron(char a, char b, char c, double **theta){
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
  int length;
  int *layers;
  double *theta;
  char name[16];
} net;

net *make_net(int length, int *layers){

 double *theta = malloc(OUT*8*sizeof(double));


  net *out = malloc(sizeof(net));

  out->length = length;
  out->layers = layers;
 out->theta = theta;
 return out;
} 


void free_net(net *x){
  printf("here\n");

 
}

double **make_theta(){


   printf("%f\n",rand()/((double)RAND_MAX));




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

int main(){

srand(time(NULL));

  int layers[3] = {2,2,1};
  net *try = make_net(3,layers);


  print_byte(89|31);

  double **this = make_theta();
  this[0][0] = 2;

  print_byte(neuron(1,89,31,this));

  free_net(try);

  return 0;
}
