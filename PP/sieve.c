#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <pthread.h>

//gcc -g -Wall -o sieve sieve.c -lpthread -lm

#define MAX_N 100000000
#define MAX_THREADS 25

int nthreads, n, prime[MAX_N+1], nextbase;
pthread_mutex_t nextbaselock = PTHREAD_MUTEX_INITIALIZER;
pthread_t id[MAX_THREADS];

void crossout(int k){
  int i;
  for(i = 3; i*k <= n; i += 2) {
    prime[i*k] = 0;
  }
}

void *worker(void * tn){
  int lim, base;
  lim = sqrt(n);
  while (1) { 
    pthread_mutex_lock(&nextbaselock);
    base = nextbase;
    nextbase += 2;
    pthread_mutex_unlock(&nextbaselock);
    if(base <= lim){
      if(prime[base]){
	crossout(base);
      }
    } else {
      return NULL;
    }
  }
}

int main(int argc, char **argv){
  int nprimes, i;
  n = atoi(argv[1]);
  nthreads = atoi(argv[2]);

  for(i=3; i<= n;i++){
    if(i%2 == 0) prime[i] = 0;
    else prime[i] = 1;
  }
  nextbase = 3;

  for(i = 0; i <nthreads; i++){
    pthread_create(&id[i], NULL, worker, (void *)i);
  }
  for(i = 0; i <nthreads; i++){
    pthread_join(id[i], NULL);
  }

  nprimes = 1;
  for(i=3; i <=n; i++){
    if(prime[i]){
      nprimes++;
    }
  }
  printf("number of primes: %d\n",nprimes);
  return 0;
}


