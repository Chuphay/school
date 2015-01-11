#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

//gcc -g -Wall -o pthread_matrix pthread_matrix.c -lpthread

//typical use case:
//./pthread_matrix 4 2
//will have 4 threads and multiply a 2X2 matrix

void *h(void *rank){
  int my_rank = (int) rank;
  printf("h %d\n", my_rank);
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


  if(argc != 3){
    printf("Error. Must supply # of threads and dimension of matrix. Exiting...\n");
    exit(1);
  }

  int thread_count = atoi(argv[1]);
  int n = atoi(argv[2]);
  pthread_t *threads = malloc(thread_count*sizeof(pthread_t));

  
  double A[n][n], x[n], y[n];
  int i,j;
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


  double my_rand = rand() / ((double)RAND_MAX+1.0); 
  printf("my_rand = %f\n", my_rand);

  

  for(i = 0; i< thread_count; i++){
    pthread_create(&threads[i], NULL, h, (void*) i);
  } 
  for(i = 0; i< thread_count; i++){
    pthread_join(threads[i], NULL);
  }

  free(threads);
  return 0;
} 
