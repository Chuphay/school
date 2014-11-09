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

double *make_input(char a, char b, char c){

  //starting to get a lot of repeat code
  //so let's try to stay with the dry principle
  //and this is also a clear point where I'm not going to even
  //pretend to do garbage clean-up anymore

  double *input = malloc(SIZE*sizeof(double));
  int n;

    for(n = 0; n < BYTE; n++){
      input[n] = !!((a << n) & 128);
      input[n+BYTE] = !!((b << n) & 128);
      input[n+2*BYTE] = !!((c << n) & 128);

    }
    return input;
}


void train(char a, char b, char c, char out, net *x, int epochs){

  double RATE = 0.5;
  printf("inside\n");
  double storage[x->size - 2][BYTE];
  // char char_stor[x->size -2];
  double input_storage[x->size -2][SIZE];
  //temp is here to hold the intermediate values


  //so i have to repeat my activate-neuron code
  //because there I was simply returning zeros and ones
  //but apparently to use the backpropagation algorithm
  //I actually need floating point numbers
  a = a|128;

  double *input = make_input(a,b,c);

  int i,n;




      for(i = 0 ; i < x->length - 2;i++){
	//printf("hidden layer\n");
   
	char b_new = activate_neuron(a,b,c,x->theta[2*i].t);
	c = activate_neuron(a,b,c,x->theta[2*i+1].t);
	b = b_new;
	//char_stor[2*i] = b;
	//char_stor[2*i+1] = c;
	for(n=0; n<BYTE; n++){
	  storage[2*i][n] = sigmoid(dot(input,x->theta[2*i].t[n]));;
	  storage[2*i+1][n] = sigmoid(dot(input, x->theta[2*i+1].t[n]));
	}
	input = make_input(a,b,c);
	for(n =0 ; n<SIZE; n++){
	  input_storage[i][n] = input[n];
	}
	//printf("i: %d\n",i);


      }

      //our final output layer

      // char_stor[x->size-3] = activate_neuron(a,b,c, x->theta[x->size -3].t);
      for(n=0; n<BYTE; n++){

	storage[x->size-3][n] =  sigmoid(dot(input, x->theta[x->size -3].t[n]));
      }


      //printf("char_stor: ");
    //print_byte(char_stor[x->size-3]);
    for(n = 0; n< epochs ; n++){
	//this code is meant to support the hidden layers
	//read what it says there
      double error1[BYTE];
      double error2[BYTE];

      for(i=0; i<BYTE; i++){
	//first the output layer
	//let's first calculate delta
	//at the output layer, this is easy:
	//\delta = (t_j-a_j)*a_j*(1 - a_j)
	error1[i] = (!!((out << i) & 128) - storage[x->size-3][i])*(storage[x->size-3][i])*(1-storage[x->size-3][i]);
	error2[i] = 0;
	//printf("here!!!!!\n");
	int j_here;
	for(j_here = 0 ; j_here <SIZE ; j_here++){
	  x->theta[x->size -3].t[i][j_here] += RATE*error1[i]*input_storage[x->length - 3][j_here];
	}


	//this code is meant to support the hidden layers
	//read what it says there

      }
	int j;
      for(j =  x->length - 2; j>0; j--){
	//now the hidden layers
	  printf("going backwards... %d\n",j);
	  //at this point, I feel so relieved that I decided to limit
	  //the network to shapes like 2X2X2X1
	  if(x->layers[j+1] == 1){
	    //this is some complicated equation,
	    //but what's worse is that layers with 2 outputs are even more 
	    //complicated.
	    //I found the complicated equation from
	    //http://www.cim.mcgill.ca/~jer/courses/ai/readings/backprop.pdf

	    //\delta_j = a_j(1 - a_j) \sum_k \delta_k \omega_{kj}
	    int m;
	    for(m = 0; m<BYTE; m++){

	      //*(x->theta[x->size -3].t[m]) -= RATE*(storage[x->size-3][m] - !!((out << m) & 128));

	      double *activation_top = storage[2*j];
	      double *activation_bottom = storage[2*j + 1];
	      //double *top_theta_to_output = 
	      //oh god... I forgot all about all those 24 theta numbers
	      //I'll have to first figure this out with pen and paper
	      int j_p;
	      for (j_p = 0;j_p< BYTE; j_p ++){ 
		error1[m] = activation_top[m]*(1 - activation_top[m])*error1[j_p]*(x->theta[x->size -3].t[m][j_p+8]);
		error2[m] = activation_bottom[m]*(1 - activation_bottom[m])*error1[j_p]*(x->theta[x->size -3].t[m][j_p+16]);
	      //printf("error1: %f j: %d\n", error1[m],j);
	      //printf("error2: %f\n",error2[m]); 
		//error1[m] =0;
		//error2[m] = 0;
	      }
	for(j_p = 0 ; j_p <SIZE ; j_p++){
	  x->theta[x->size - 5].t[m][j_p] += RATE*error1[m]*input_storage[x->length - 5][j_p];
	  x->theta[x->size - 4].t[m][j_p] += RATE*error2[m]*input_storage[x->length - 4][j_p];

	}


	    }
	    
	    //where a_j is the activation here and now
	    //delta_k is the previous level error
	    //and omega is between now and the past 
	    //(but remember we're going backwards)

	    //and now we change the weights from now going into the future
	    //(again we're going backwards)
	    //hmmm...
	    //I can see a problem here
	    //we're changing the weights 
	    //and then in the next iteration we are using those weights
	    //in our calculations...
	    //oh well... not enough time to dwell on this

	    //\omega_{ij} <-- \omega_{ij} + \eta \delta_j a_i
	    
	    //where here a_i is the activation from the future
	    //and eta is the learning rate, to be adjusted later






	  } else if (x->layers[j+1] == 2){

	    printf("inside this else if. j = %d \n",j);
	    int m;
	    for(m = 0; m<BYTE; m++){

	      //*(x->theta[x->size -3].t[m]) -= RATE*(storage[x->size-3][m] - !!((out << m) & 128));

	      double *activation_top = storage[2*j];
	      double *activation_bottom = storage[2*j + 1];
	      //double *top_theta_to_output = 
	      //oh god... I forgot all about all those 24 theta numbers
	      //I'll have to first figure this out with pen and paper
	      int j_p;
	      for (j_p = 0;j_p< BYTE; j_p ++){ 
		double sum1 = error1[j_p]*(x->theta[2*(j)].t[m][j_p+8]) + error2[j_p]*(x->theta[2*(j)].t[m][j_p+8]);
		double sum2 = error1[j_p]*(x->theta[2*(j)+1].t[m][j_p+16]) + error2[j_p]*(x->theta[2*(j)+1].t[m][j_p+16]);

		error1[m] = activation_top[m]*(1 - activation_top[m])*sum1;
		error2[m] = activation_bottom[m]*(1 - activation_bottom[m])*sum2;
	      //printf("error1: %f j: %d\n", error1[m],j);
	      //printf("error2: %f\n",error2[m]); 
		//error1[m] =0;
		//error2[m] = 0;
	      }
	for(j_p = 0 ; j_p <SIZE ; j_p++){
	  x->theta[2*(j-1)].t[m][j_p] += RATE*error1[m]*input_storage[2*j][j_p];
	  x->theta[2*(j-1) + 1].t[m][j_p] += RATE*error2[m]*input_storage[2*j + 1][j_p];

	}

	    }
	  } else {
	    //this is just some check code...
	    //if you see this, there has been an error...
	    printf("if you see this, there has been an error...\n");
	  printf("going backwards... %d\n",j);
	  printf("Previous layer length: %d\n", x->layers[j+1]);

	  }

	 
      }
    }
					 					  
}



int main(){

  srand(time(NULL));
  
  int layers[5] = {2,2,2,2,1};
  net *try = make_net(9,5,layers);
  printf("try length: %d\n", try->length);
  print_byte(activate_net(0,12,75,try));
  print_byte(12^75);
  train(0,12,75,12^75,try,1);
  printf("here : ");
  print_byte(activate_net(0,12,75,try));

  printf("small\n");
  int layers2[2] = {2,1};
  net *try2 = make_net(3,2,layers2);
  print_byte(activate_net(0,129,87,try2));
  //print_byte(12|75);
  //train(0,12,75,12|75,try2,10);
  train(0,129,87,129|87,try2,200);
  print_byte(activate_net(0,129,87,try2));
  print_byte(129|87);
  


  //free_net(try);

  return 0;
}
