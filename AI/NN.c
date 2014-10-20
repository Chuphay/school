#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define MAX 8


void print_binary(char a){
  int i;
  for(i = 0; i < MAX; i++) {
      printf("%d", !!((a << i) & 128));
  }
  printf("\n");

}

void print_16(char a, char b){
  int i;
  for(i = 0; i < 2*MAX; i++) {
    if(i<MAX){
      printf("%d", !!((a << i) & 128));
    } else {
      printf("%d", !!((b << i%MAX) & 128));
    }
  }
  printf("\n");
}

void print16(char a[MAX]){
  int i;
  for(i = 0; i < 2*MAX; i++) {
    printf("%d", a[i]);

   
  }
  printf("\n");
}

void XOR(char a, char b){
  char z = a | b;
  char y = ~(a & b);
  if((z&y) != (a^b)){
    printf("yikes... XOR not working \n");
    exit(1);
  }
  print_binary(z&y);
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
  for(i = 0; i < 2*MAX; i++){
    out += a[i]*b[i];
  }
  return out;
} 

char and_net(char a, char b){

  if((a & 128) || (b & 128)){
    printf("hmm... I have a leading 1... exiting\n");
    exit(1);
  }
  printf("a: ");
  print_binary(a);
  printf("shifted and adding 1: ");
  a = (a<<1)+1;
  print_binary(a);
  printf("b: ");
  print_binary(b);
  double theta[MAX][2*MAX] = {};
  double input[2*MAX];

  int i;
  for( i = 0; i < MAX; i++){
    input[i] = !!((b << i) & 128);
    input[i+MAX] = !!((a << i) & 128);
  }

  //printf("input: \n");
  //print16(input);

  for( i = 1; i< MAX; i++){
    
    theta[i][i-1] = 20;
    theta[i][i+7] = 20;
    theta[i][2*MAX-1] = -30;
		     		     
  }

  printf("theta: \n");
  int m,n;
  for(m = 0; m < MAX; m++){
    for(n = 0; n< 2*MAX; n++){
      printf("%0.2f ", theta[m][n]);
    }
    printf("\n");
  } 

  printf("here: %f there: %f\n", input[1], theta[0][1]);
  print_16(b,a);
  char out = 0;
  for (i = 0 ; i< MAX; i++){
    double temp;  
    temp = sigmoid(dot(  (double *)input,theta[i] ));
    printf("%0.2f\n",temp);
    if(temp>0.5){
      out = out|(1<<i);
    }
  }
  print_binary(out);
  return out ;

}


typedef struct node{
  char out;

} node;

int main(){

  printf("on\n");
  char c = '0';
  printf("%d\n",c);

  //char m = getchar();
  char m = 0x30;
  printf("%u\n", m-'0'-1);
  

  print_binary(6 << 2);
  print_binary(24);
  print_binary(255);
  //print_binary(255<<1);
  print_binary(255>>1);
  print_binary(128);
  print_binary(0x80);
  print_binary(213);
  print_binary(88);
  XOR(213,88);
  printf("sigmoid\n");
  printf("%f, %f, %f\n", sigmoid(0), sigmoid(3.5), sigmoid(-5));
  double aa[2*MAX] = {0,1,2,3,4,5,6,7};
  printf("%d\n",dot(aa,aa) == 140);


  and_net(121,19);

  node d;
  printf("size of empty struct: %d\n", sizeof(d));


  return 0;
}
