cities <- c("San Rafael", "Novato","Vallejo","Pinole",
            "Concord", "Richmond","Walnut Creek", "Berkeley",
            "Oakland","San Leandro","Hayward",
            "Pleasonton","Fremont","San Jose","Palo Alto",
            "San Mateo","Baywood Park","Half Moon Bay","Pacifica",
            "Daly City", "San Francisco")
bay.area<- matrix(,nrow = length(cities),ncol = length(cities))
bay.area.highway <- matrix(,nrow = length(cities),ncol = length(cities))
colnames(bay.area) <- cities
rownames(bay.area) <- cities
colnames(bay.area.highway) <- cities
rownames(bay.area.highway) <- cities

make_data <- function( city1, city2, highway, time){
  bay.area[city1, city2] <<- time #check http://stackoverflow.com/questions/1236620/global-variables-in-r
  bay.area[city2, city1] <<- time 
  bay.area.highway[city1,city2] <<- highway
  bay.area.highway[city2,city1] <<- highway
}
make_data("San Rafael","Novato", "101", 15)
make_data("San Rafael","Richmond", "580", 20)
make_data("San Rafael","San Francisco", "101", 25)
make_data("Novato","Vallejo", "37", 22)
make_data("Vallejo","Pinole", "80", 7)
make_data("Vallejo","Concord", "780", 12)
make_data("Concord","Pinole", "4", 14)
make_data("Concord","Walnut Creek", "680", 17)
make_data("Richmond","Pinole", "80", 6)
make_data("Berkeley","Richmond", "580", 18)
make_data("Berkeley","Walnut Creek", "24", 22)
make_data("Berkeley","Oakland", "24", 5)
make_data("Berkeley","Hayward", "580", 33)
make_data("Oakland","San Leandro", "880", 18)
make_data("San Leandro","Hayward", "238", 12)
make_data("San Leandro","San Mateo", "92", 21)
make_data("San Leandro","Fremont", "880", 16)
make_data("Fremont","San Jose", "880", 15)
make_data("Fremont","Palo Alto", "84", 20)
make_data("San Jose","Pleasonton", "680", 26)
make_data("San Jose","Palo Alto", "101", 13)
make_data("Palo Alto","San Mateo", "101", 18)
make_data("Baywood Park","San Mateo", "92", 7)
make_data("San Francisco","San Mateo", "101", 21)
make_data("Baywood Park","Half Moon Bay", "92", 12)
make_data("Half Moon Bay","Pacifica", "1", 17)
make_data("Daly City","Pacifica", "1", 13)
make_data("San Francisco","Daly City", "280", 11)
make_data("San Francisco","Oakland", "80", 21)
make_data("Baywood Park","Daly City", "280", 12)
make_data("San Jose", "Baywood Park", "280", 15)
make_data("Pleasonton","Walnut Creek","680",26)
make_data("Pleasonton","Hayward","580",19)

DFS <- function(start, end, visited){
  visited <- c(visited, start)
  for (x in colnames(bay.area)){
    if(is.element(end, visited)){
      break
    }
    if(!is.na(bay.area[start,x]) && !(is.element(x, visited))){
      if(x == end){
        visited <- c(visited,end)
        break
      } else {
        visited <- DFS(x, end, visited)
      }
    }
  }
  visited
}
DFS("Berkeley","Oakland",c())

#Stacks and Queues
stack.max <- 3
stack.values <- numeric(stack.max)
stack.length <- 0

stack.push <- function(x){
  if(stack.length<stack.max){
    stack.length <<- stack.length + 1
    stack.values[stack.length] <<- x
  } else {
    cat("Error, stack is filled.\n")
  }
}
stack.pop <- function(){
  if(stack.length>0){
    stack.length <<- stack.length - 1
    stack.values[stack.length+1]
  } else {
    cat("Error, stack is empty.\n")
  }
}
stack.items <- function(){
  out <- c()
  i <- 0
  while(i < stack.length){
    out <- c(out, stack.values[i])
    i <- i + 1
  }
  out
}
stack.initialize <- function(n){
  stack.max <<- n
  stack.values <<- numeric(stack.max)
  stack.length <<- 0
}
#apparently stack.push("Hello World!") works, but we could have also tried:
stack.values <- character(stack.max)


#Queue
queue.max <- 3
queue.values <- numeric(queue.max)
queue.start <- 0
queue.length <- 0

queue.push <- function(x){
  if(queue.length < queue.max){
    spot <- (queue.start + queue.length)%%queue.max+1
    queue.length <<- queue.length + 1
    queue.values[spot] <<- x
  } else {
    cat("Error, queue is filled.")
  }
}
queue.pop <- function(){
  if(queue.length>0){
    queue.start <<- queue.start%%queue.max + 1
    queue.length <<- queue.length - 1
    queue.values[queue.start]
  } else {
    cat("Error, queue is empty.")
  }
}
queue.items <- function(){
  out <- c()
  i <- 0
  while (i < queue.length){
    out <- c(out, queue.values[(queue.start + i)%%queue.max +1])
    i <- i + 1
  }
  out
}
queue.initialize <- function(n){
  queue.max <<- n
  queue.values <<- numeric(queue.max)
  queue.start <<- 0
  queue.length <<- 0
}

DFS_Stack <- function(start, end){
  stack.initialize(120)
  visited <- c()
  stack.push(start)
  while(stack.length>0){
    if(is.element(end, visited)){
      break
    }
    spot <- stack.pop()
    visited <- c(visited, spot)
    for (x in colnames(bay.area)){
      if(!is.na(bay.area[start,x]) && !(is.element(x, visited))){
        stack.push(x)
      }
    }
  }
  visited
}

BFS_Queue <- function(start, end){
  queue.initialize(120)
  visited <- c()
  queue.push(start)
  while(stack.length>0){
    if(is.element(end, visited)){
      break
    }
    spot <- queue.pop()
    visited <- c(visited, spot)
    for (x in colnames(bay.area)){
      if(!is.na(bay.area[start,x]) && !(is.element(x, visited))){
        queue.push(x)
      }
    }
  }
  visited
  
}


