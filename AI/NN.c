
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




char neuron(char a, char b, char c, double **theta){
  //char a is simply the control bit
  //it's for future flexibility
  //and can be set to anything... for now
  //we set it to to 128 so that its binary representation is
  //10000000
  //and we use this as something like
  //an electrical ground against the inputs

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
  int inputs;
  int outputs;
  int length;
  int *layers;
  double ***theta;
  char name[16];
} net;

net make_net(int a,int b , int c){

 double ***theta = malloc(OUT*sizeof(double **));
 int i,j;
 for(j = 0; j<IN; j++){
   theta[j] = malloc(3*sizeof(double));
   for(i= 0;i<OUT;i++){
    theta[j][i] = malloc(IN*sizeof(double));
  }
 }
  for(i = 0; i< BYTE; i++){
    
    theta[0][i][i+BYTE] = 30;
    theta[0][i][i+2*BYTE] = 30;
    theta[0][i][0] = -20;
		     		     
  }

  net out; //= malloc(sizeof(net));
 out.theta = theta;
 return out;
} 




int main(){
  net try = make_net(2,1,4);

  int i;
  double **theta = malloc(OUT*sizeof(double *));
  for(i= 0;i<OUT;i++){
    theta[i] = malloc(IN*sizeof(double));
  }
  for(i = 0; i< BYTE; i++){
    
    theta[i][i+BYTE] = 30;
    theta[i][i+2*BYTE] = 30;
    theta[i][0] = -20;
		     		     
  }


  print_byte(89|31);
  print_byte( neuron(1,89,31,theta));

  return 0;
}
