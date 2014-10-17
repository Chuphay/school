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
  double theta[2*MAX];
  int i;
  for( i = 0; i< 2*MAX; i++){
    theta[i] = 20;
  }
  theta[2*MAX-1] = -30;

  return b;
}




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


  and_net(120,19);


  return 0;
}
