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

void print16(char a[MAX]){
  int i;
  for(i = 0; i < 2*MAX; i++) {
    printf("%d", a[i]); 
  }
  printf("\n");
}

void print_theta(double theta[MAX][2*MAX]){

  printf("theta: \n");
  int m,n;
  for(m = 0; m < MAX; m++){
    for(n = 0; n< 2*MAX; n++){
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
  for(i = 0; i < 2*MAX; i++){
    out += a[i]*b[i];
  }
  return out;
} 

char and_net(char a, char b){

  if((a & 128)){
    printf("hmm... I have a leading 1... exiting\n");
    exit(1);
  }
  if(b&128){
    printf("Leading 1... un-qualified entry... but not going to exit\n");
  }
  
  //shifting a over one step to create a bias
  //our previous condition should have checked to make sure
  //this was valid
  a = (a<<1)+1;

  double theta[MAX][2*MAX] = {};
  double input[2*MAX];

  int i;
  for(i = 0; i < MAX; i++){
    input[i] = !!((b << i) & 128);
    input[i+MAX] = !!((a << i) & 128);
  }


  for(i = 1; i< MAX; i++){
    
    theta[i][i] = 20;
    theta[i][i+7] = 20;
    theta[i][2*MAX-1] = -30;
		     		     
  }

  //these lines are to deal with the leading one in b
  //it'll basically let the leading one pass
  theta[0][0] = 40;
  theta[0][2*MAX - 1] = -30;


  //print_theta(theta);

  char out = 0;
  for (i = 0 ; i< MAX; i++){
    double temp;  
    temp = sigmoid(dot(  (double *)input,theta[i] ));

    if(temp>0.5){
      out = out|(1<<(MAX-i-1));
    }
  }
  //print_byte(out);
  return out ;

}



char or_net(char a, char b){

  if((a & 128)){
    printf("hmm... I have a leading 1... exiting\n");
    exit(1);
  }
  if(b&128){
    printf("Leading 1... un-qualified entry... but not going to exit\n");
  }
  
  //shifting a over one step to create a bias
  //our previous condition should have checked to make sure
  //this was valid
  a = (a<<1)+1;

  double theta[MAX][2*MAX] = {};
  double input[2*MAX];

  int i;
  for(i = 0; i < MAX; i++){
    input[i] = !!((b << i) & 128);
    input[i+MAX] = !!((a << i) & 128);
  }


  for(i = 1; i< MAX; i++){
    
    theta[i][i] = 30;
    theta[i][i+7] = 30;
    theta[i][2*MAX-1] = -20;
		     		     
  }

  //these lines are to deal with the leading one in b
  //it'll basically let the leading one pass
  theta[0][0] = 40;
  theta[0][2*MAX - 1] = -30;


  //print_theta(theta);

  char out = 0;
  for (i = 0 ; i< MAX; i++){
    double temp;  
    temp = sigmoid(dot(  (double *)input,theta[i] ));

    if(temp>0.5){
      out = out|(1<<(MAX-i-1));
    }
  }
  //print_byte(out);
  return out ;

}






char not_net(char a, char b){
  //char a is simply the control bit
  //and can be safely ignored


  if((a & 128)){
    printf("hmm... I have a leading 1... exiting\n");
    exit(1);
  }
  if(b&128){
    printf("Leading 1... un-qualified entry... but not going to exit\n");
  }
  
  //shifting a over one step to create a bias
  //our previous condition should have checked to make sure
  //this was valid
  a = (a<<1)+1;

  double theta[MAX][2*MAX] = {};
  double input[2*MAX];

  int i;
  for(i = 0; i < MAX; i++){
    input[i] = !!((b << i) & 128);
    input[i+MAX] = !!((a << i) & 128);
  }


  for(i = 0; i< MAX; i++){
    
    theta[i][i] = -40;
    //theta[i][i+7] = 30;
    theta[i][2*MAX-1] = 30;
		     		     
  }



  //print_theta(theta);

  char out = 0;
  for (i = 0 ; i< MAX; i++){
    double temp;  
    temp = sigmoid(dot(  (double *)input,theta[i] ));

    if(temp>0.5){
      out = out|(1<<(MAX-i-1));
    }
  }

  return out ;
}


char xor_net(char a, char b){


  char z = or_net(a, b);
  char y = not_net(1,and_net(a, b));
  return and_net(z,y);
}



int main(){

  print_byte(and_net(39,121));

  print_byte(39&121);

  print_byte(or_net(89,31));
  print_byte(89|31);
  print_byte(not_net(7,or_net(89,31)));
  print_byte(~(89|31));
  print_byte(xor_net(88,113));
  print_byte(88^113);
  return 0;
}
