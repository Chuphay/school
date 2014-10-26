#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define MAX 8


void print_byte(char a){
  int i;
  for(i = 0; i < MAX; i++) {
      printf("%d", !!((a << i) & 128));
  }
  printf("\n");
}

void print_theta(double theta[MAX][3*MAX]){

  printf("theta: \n");
  int m,n;
  for(m = 0; m < MAX; m++){
    for(n = 0; n< 3*MAX; n++){
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
  for(i = 0; i < 3*MAX; i++){
    out += a[i]*b[i];
  }
  return out;
} 


typedef struct node {
  int value;
  double theta[MAX][3*MAX];

} node;

char stoned_net(char a, char b, char c, node x){
  //char a is simply the control bit
  //and can be safely ignored

  a = a|128;
  

  double input[3*MAX];

  int i;
  for(i = 0; i < MAX; i++){
    input[i] = !!((a << i) & 128);
    input[i+MAX] = !!((b << i) & 128);
    input[i+2*MAX] = !!((c << i) & 128);
  }

  char out = 0;
  for (i = 0 ; i< MAX; i++){
    double temp;  
    temp = sigmoid(dot(input, x.theta[i]));

    if(temp>0.5){
      out = out|(1<<(MAX-i-1));
    }
  }

  return out ;
}




int main(){


  node or;

  int m,n;
  for(m = 0; m < MAX; m++){
    for(n = 0; n< 3*MAX; n++){
      or.theta[m][n] = 0;
    }
  }

  int i;
  for(i = 0; i< 8; i++){
    
    or.theta[i][i+8] = 30;
    or.theta[i][i+16] = 30;
    or.theta[i][0] = -20;
		     		     
  }

  print_byte(89|31);
  print_byte( stoned_net(1,89,31,or));

  return 0;
}
