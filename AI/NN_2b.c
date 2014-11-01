
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>



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


char neuron(char a, char b, char c, double *theta){
  a = a|128;

  double input[SIZE];

  int i;
  for(i = 0; i < BYTE; i++){
    input[i] = !!((a << i) & 128);
    input[i+BYTE] = !!((b << i) & 128);
    input[i+2*BYTE] = !!((c << i) & 128);
  }

  char out = 0;
  for (i = 0 ; i< 3*BYTE; i = i + BYTE){
    double temp;  
    temp = sigmoid(dot(input, &theta[i], SIZE));

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




int main(){

srand(time(NULL));
 
  printf("%f\n",rand()/((double)RAND_MAX));
  print_byte(89|31);

  double *this = malloc(24*sizeof(double));
  this[0] = 2;
 for(i = 1; i< MAX; i++){
    
    theta[i][i] = 30;
    theta[i][i+7] = 30;
    theta[i][2*MAX-1] = -20;
		     		     
  }

  print_byte(neuron(1,89,31,this));


  return 0;
}
