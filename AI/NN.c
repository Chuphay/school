#include <stdio.h>
#include <stdlib.h>
#include <math.h>


void print_binary(char a){
  int i;
  for (i = 0; i < 8; i++) {
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


int main(){

  printf("on\n");
  char c = '0';
  printf("%d\n",c);

  //char m = getchar();
  char m = 0x30;
  printf("%u\n", m-'0'-1);
  

  double x = 0;
  
  printf("The exponential value of %lf is %lf\n", x, exp(x));
  printf("The exponential value of %lf is %lf\n", x+1, exp(x+1));
  printf("The exponential value of %lf is %lf\n", x+2, exp(x+2));

  char a = 10;
  int i;
  for (i = 0; i < 8; i++) {
      printf("%d", !!((a << i) & 0x80));
  }
  printf("\n");

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



  return 0;
}
