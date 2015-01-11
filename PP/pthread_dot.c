#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

//gcc -g -Wall -o pthread_dot pthread_dot.c -lpthread
//run it as follows:
//time ./pthread_dot 2
//Most of the code is from
//https://computing.llnl.gov/tutorials/pthreads/#Mutexes

typedef struct {
  double *a, *b, sum;
  int length;
} DOTDATA;


#define VECLEN 10000000

DOTDATA dotstr;
pthread_mutex_t mutexsum;

void *dotprod (void *rank){

  int i, start, end, len;
  double my_sum, *x, *y;
  int my_rank = (int) rank;

  len = dotstr.length;
  start = my_rank*len;
  end = start + len;
  x = dotstr.a;
  y = dotstr.b;

  my_sum = 0;
  for(i = start; i<end; i++){
    my_sum += x[i]*y[i];
  }

  pthread_mutex_lock(&mutexsum);
  dotstr.sum += my_sum;
  pthread_mutex_unlock(&mutexsum);

  return 0;
}

int main(int argc, char *argv[]){
  printf("%d\n",atoi(argv[1]));
  int thread_num = atoi(argv[1]);
  pthread_t threads[thread_num];

  int i;
  double *x = malloc(thread_num*VECLEN*sizeof(double));
  double *y = malloc(thread_num*VECLEN*sizeof(double));

  for (i = 0; i < thread_num*VECLEN; i++){
    x[i] = 1;
    y[i] = 1;
  }

  dotstr.length = VECLEN;
  dotstr.a = x;
  dotstr.b = y;
  dotstr.sum = 0;

  pthread_mutex_init(&mutexsum, NULL);

  for(i = 0; i<thread_num; i++){
    pthread_create(&threads[i], NULL, dotprod, (void *) i);
  }

  for(i = 0; i<thread_num; i++){
    pthread_join(threads[i], NULL);
  }

  printf("Sum = %f\n", dotstr.sum);
  free(x);
  free(y);
  pthread_mutex_destroy(&mutexsum);

  return 0;
}

