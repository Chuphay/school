#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

#define MAX_THREADS 1024
#define MSG_MAX 100

int thread_count;
char **messages;
sem_t *semaphores;

void Usage(char *prog_name) {
  fprintf(stderr, "usage: %s <number of threads>\n", prog_name);
  exit(0);
}

void *Send_msg(void *rank){

  int my_rank = (int) rank;
  int dest = (my_rank + 1) % thread_count;
  char *my_msg = malloc(MSG_MAX*sizeof(char));

  sprintf(my_msg, "Hello to %d from %d", dest, my_rank);
  messages[dest] = my_msg;
  sem_post(&semaphores[dest]);
  
  sem_wait(&semaphores[my_rank]);
  printf("Thread %d > %s\n", my_rank, messages[my_rank]);

  return 0;
}

int main(int argc, char *argv[]){

  int thread;
  pthread_t *threads;

  if(argc != 2) Usage(argv[0]);
  thread_count = atoi(argv[1]);

  threads = malloc(thread_count*sizeof(pthread_t));
  messages = malloc(thread_count*sizeof(char*));
  semaphores = malloc(thread_count*sizeof(sem_t));
  for(thread = 0; thread < thread_count; thread++){
    messages[thread] = NULL;
    sem_init(&semaphores[thread],0,0); 
  }
  for(thread = 0; thread < thread_count; thread++){
    pthread_create(&threads[thread], NULL, Send_msg, (void *) thread);
  }
  for(thread = 0; thread < thread_count; thread++){
    pthread_join(threads[thread], NULL);
  }
  for(thread = 0; thread < thread_count; thread++){
    free(messages[thread]);
    sem_destroy(&semaphores[thread]);
  }
  free(messages);
  free(semaphores);
  free(threads);

  return 0;
}
