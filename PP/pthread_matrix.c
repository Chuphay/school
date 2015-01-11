#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

//gcc -g -Wall -o pthread_matrix pthread_matrix.c -lpthread

//typical use case:
//./pthread_matrix 4


//We'll use some global variables as suggested by my book
#define N 43
int thread_count;
double A[N][N], x[N], y[N];

void *h(void *rank){
  int extra = N%thread_count;
  int count = N/thread_count;
  int my_rank = (int) rank;
  int i,j, start, end;
  if(extra - my_rank>0){
    start = my_rank*count + my_rank;
    end =  (my_rank+1)*count+my_rank+1;
    for(i = start; i < end; i++){
      y[i]= 0;
      for(j = 0; j<N; j++){
	y[i] += A[i][j]*x[j];
      }
    }
  } else {
    start =  my_rank*count + extra;
    end =  (my_rank+1)*count+extra;
    for(i = start; i < end; i++){
      y[i]= 0;
      for(j = 0; j<N; j++){
	y[i] += A[i][j]*x[j];
      }
    }

  }

  return NULL;
}
 

double sum(double *v, int n){
  int i;
  double out = 0;
  for(i = 0; i<n; i++){
    out += v[i];
  }
  return out;
}

int main(int argc, char *argv[]){


  if(argc != 2){
    printf("Error. Must supply # of threads. Exiting...\n");
    exit(1);
  }

  thread_count = atoi(argv[1]);
  pthread_t *threads = malloc(thread_count*sizeof(pthread_t));

  

  int i,j;
  int n = N;
  for(i = 0; i<n; i++){
    x[i]= 1;
    for(j = 0; j<n; j++){
      A[i][j] = j+1;
    }
  }
  for(i = 0; i<n; i++){
    y[i]= 0;
    for(j = 0; j<n; j++){
      y[i] += A[i][j]*x[j];
    }
  }
  printf("sum = %f\n", sum(y, n));
  for(i = 0; i<n; i++){
    y[i]= 0; //clear y just to make sure my algorithm is working
  }
 
  for(i = 0; i< thread_count; i++){
    pthread_create(&threads[i], NULL, h, (void*) i);
  } 
  for(i = 0; i< thread_count; i++){
    pthread_join(threads[i], NULL);
  }
 
  printf("sum = %f\n", sum(y, n));



  free(threads);
  return 0;
} 


