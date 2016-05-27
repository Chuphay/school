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

San_Rafael <- c("San Rafael",37.9735,-122.5311)
Novato <- c("Novato",38.1074,-122.5697)
Vallejo <- c("Vallejo",38.1041,-122.2566)
Pinole <- c("Pinole",38.0044,-122.2989)
Concord <- c("Concord",37.9780,-122.0311)
Richmond <- c("Richmond",37.9358,-122.3477)
Walnut_Creek <- c("Walnut Creek",37.9101,-122.0652)
Berkeley <- c("Berkeley", 37.8716,-122.2727)
Oakland <- c("Oakland", 37.8044,-122.2711)
San_Leandro <- c("San Leandro",37.7249,-122.1561)
Hayward <- c("Hayward", 37.6688,-122.0808)
Pleasonton <- c("Pleasonton", 37.6624,-121.8747)
Fremont <- c("Fremont", 37.5483,-121.9886)
San_Jose <- c("San Jose", 37.3382,-121.8863)
Palo_Alto <- c("Palo Alto", 37.4419,-122.1430)
San_Mateo <- c("San Mateo", 37.5630,-122.3255)
Baywood_Park <- c("Baywood Park", 37.5244,-122.3476)
Half_Moon_Bay <- c("Half Moon Bay", 37.4636,-122.4286)
Pacifica <- c("Pacifica", 37.6138,-122.4869)
Daly_City <- c("Daly City", 37.6879,-122.4702)
San_Francisco <- c("San Francisco", 37.7749,-122.4194)
city.location <- list(San_Rafael, Novato, Vallejo, Pinole,
                      Concord, Richmond,Walnut_Creek, Berkeley,
                      Oakland, San_Leandro, Hayward,
                      Pleasonton, Fremont, San_Jose, Palo_Alto,
                      San_Mateo, Baywood_Park, Half_Moon_Bay,Pacifica,
                      Daly_City, San_Francisco)

get_location <- function(name){
  out <- c(-1,-1)
  for (x in city.location){
    if(name == x[1]){
      out[1] <- as.numeric(x[2])
      out[2] <- as.numeric(x[3])
    }
  }
  out
}
calc_dist <- function(city1, city2){
  x <- get_location(city1)
  y <- get_location(city2)
  100*sqrt((x[1]-y[1])^2 + (x[2]-y[2])^2)
}

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


A_Star <- function (start, goal){
  closedSet <- c()
  openSet <- c(start)
  fScore <- c(calc_dist(start, goal))
  cameFrom <- c() 

  while (length(openSet) >0){
    current <- ""
    minF <- 1000000
    for(i in length(fScore)){
      if(fScore[i]< minF){
        minF <- fScore[i]
        current <- openSet[i]
      }
    }
    print(current)
    print("here")
    if (current == goal){
      print("Yay")
      break
    } 
  
    index <- which(openSet == current)
    openSet <- openSet[-index]
    fScore[-index]
    closedSet <- c(closedSet, current)
    for (x in colnames(bay.area)){
      if(!is.na(bay.area[current,x]) && !(is.element(x, closedSet))){
        if(x == goal){
          print("other Yay")
          openSet <- c(openSet, x)
          fScore <- c(fScore, bay.area[current,x]+calc_dist(current, goal))
          break
        } else {
          openSet <- c(openSet, x)
          fScore <- c(fScore, bay.area[current,x]+calc_dist(current, goal))
        }
      }
    }
  }
}

A_Star("Berkeley", "Oakland")
