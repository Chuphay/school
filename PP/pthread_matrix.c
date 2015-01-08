#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void *h(void *rank){
  int my_rank = (int) rank;
  printf("h %d\n", my_rank);
  return NULL;
} 

int main(int argc, char *argv[]){

  if(argc != 2){
    printf("Error. Must supply # of threads. Exiting...\n");
    exit(1);
  }

  int thread_count = atoi(argv[1]);
  pthread_t *threads = malloc(thread_count*sizeof(pthread_t));

  int i;

  for(i = 0; i< thread_count; i++){
    pthread_create(&threads[i], NULL, h, (void*) i);
  } 
  for(i = 0; i< thread_count; i++){
    pthread_join(threads[i], NULL);
  }

  free(threads);
  return 0;
} 
