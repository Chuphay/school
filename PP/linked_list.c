#include <stdio.h>
#include <stdlib.h>

struct list {
  int data;
  struct list *next;
};

int member(int value, struct list *head){
  struct list *current = head;
  int out = 0;
  while(current != NULL){
    if(current->data == value){
      out = 1;
      break;
    } else {
      current = current->next;
    }
  }
  return out;
}

void insert(int value, struct list **head){
  struct list *current = *head;
  struct list *previous = NULL;
  while(current != NULL){
    previous = current;
    current = current->next;
  }
  struct list *new = malloc(sizeof(struct list));
  new->data = value;
  new->next = NULL;
  if(previous == NULL){ //new first node
    *head = new;   
  } else {
    previous->next = new;
  }
}

int delete(int value, struct list **head){
  struct list *current = *head;
  struct list *previous = NULL;
  int out = 0;
  while(current != NULL){
    if(current->data == value){
      out = 1;
      previous->next = current->next;
      free(current);
      break;
    } else {
      previous = current;
      current = current->next;
    }
  }
  return out;
} 

void destroy_list(struct list *head){
  struct list *current = head;
  while(current != NULL){
    struct list *tmp = current;
    current = current->next;
    free(tmp);
  }
}


int main(){
  struct list *head = NULL;
  insert(3, &head);
  insert(4, &head);
  insert(23, &head);
  insert(55, &head);
  printf("has 4: %d\n", member(4, head));
  delete(4, &head);
  printf("has 4: %d\n", member(4, head));
  destroy_list(head);
  
  return 0;
}
