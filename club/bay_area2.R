#
#  The program implements A* algorithm
#  @author  Daming Lu
#  @version 1.0
#  @since   2015-05-06 
# 

library(ggmap)
library(ggplot2)


cities <- c("San Rafael", "Novato","Vallejo","Pinole",
            "Concord", "Richmond","Walnut Creek", "Berkeley",
            "Oakland","San Leandro","Hayward",
            "Pleasonton","Fremont","San Jose","Palo Alto",
            "San Mateo","Baywood Park","Half Moon Bay","Pacifica",
            "Daly City", "San Francisco")

n_cities = length(cities)

bay.area <- matrix(Inf, nrow = length(cities), ncol = length(cities))
colnames(bay.area) <- cities
rownames(bay.area) <- cities

make_data <- function(city1, city2, highway, distance){
  bay.area[city1, city2] <<- distance 
  bay.area[city2, city1] <<- distance 
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


San_Rafael <- c(37.9735,-122.5311)
Novato <- c(38.1074,-122.5697)
Vallejo <- c(38.1041,-122.2566)
Pinole <- c(38.0044,-122.2989)
Concord <- c(37.9780,-122.0311)
Richmond <- c(37.9358,-122.3477)
Walnut_Creek <- c(37.9101,-122.0652)
Berkeley <- c(37.8716,-122.2727)
Oakland <- c(37.8044,-122.2711)
San_Leandro <- c(37.7249,-122.1561)
Hayward <- c(37.6688,-122.0808)
Pleasonton <- c(37.6624,-121.8747)
Fremont <- c(37.5483,-121.9886)
San_Jose <- c(37.3382,-121.8863)
Palo_Alto <- c(37.4419,-122.1430)
San_Mateo <- c(37.5630,-122.3255)
Baywood_Park <- c(37.5244,-122.3476)
Half_Moon_Bay <- c(37.4636,-122.4286)
Pacifica <- c(37.6138,-122.4869)
Daly_City <- c(37.6879,-122.4702)
San_Francisco <- c(37.7749,-122.4194)
df <-data.frame(list(San_Rafael, Novato,Vallejo,Pinole,
                  Concord, Richmond, Walnut_Creek, Berkeley,
                  Oakland, San_Leandro, Hayward,
                  Pleasonton,Fremont,San_Jose,Palo_Alto,
                  San_Mateo,Baywood_Park,Half_Moon_Bay,Pacifica,
                  Daly_City, San_Francisco))
colnames(df) <- cities



heuristic_cost_estimate <- function(start, end) {
  
  city_index_start = match(start, cities)
  city_index_end = match(end, cities)
  
  start_x = as.numeric(df[1,city_index_start])
  start_y = as.numeric(df[2,city_index_start])

  end_x = as.numeric(df[1,city_index_end])
  end_y = as.numeric(df[2,city_index_end])
  
  dist = 100*sqrt((start_x-end_x)^2 + (start_y-end_y)^2)
  dist
}

reconstruct_path <- function(cameFrom, current) {
  total_path = c(current[[1]])
  while (is.element(current, names(cameFrom))) {
    current = cameFrom[current]
    if(!is.na(current)){
      total_path = c(total_path, current[[1]])
    }
  }
  return(total_path)
}

AStar <- function(start, end) {
  if(!any(cities==start) || !any(cities==end)) {  
    return ('start city or end city not in cities')
  }
  
  closedSet = c()
  openSet = c(start)
  
  gScore <- rep(NA, n_cities)
  names(gScore) <- cities
  
  fScore <- rep(NA, n_cities)
  names(fScore) <- cities

  cameFrom <- rep(NA, n_cities)
  names(cameFrom) <- cities
    
  gScore[start] = 0
  fScore[start] = heuristic_cost_estimate(start, end)
  
  while(length(openSet) > 0) {
    # find the city with lowest fScore
    lowest_fScore = Inf
    lowest_city = ''
    lowest_city_index = 0
    
    for (i in 1:length(openSet)) {
      if (fScore[openSet[i]] < lowest_fScore) {
        lowest_fScore = fScore[openSet[i]]
        lowest_city = openSet[i]
        lowest_city_index = i
      }
    }
    
    output <- c()
    # found!
    if(lowest_city == end) {
      print("success")
      output <- reconstruct_path(cameFrom, lowest_city)
      break
    }
    
    # remove and add
    removeCity = openSet[lowest_city_index]
    openSet = openSet[-lowest_city_index]
    closedSet = c(closedSet, lowest_city)

    gScore_current = gScore[lowest_city]
    
    for(i in 1:length(bay.area[lowest_city, ])) {
      neighbor_dist = bay.area[lowest_city, i]
      neighbor_city = colnames(bay.area)[i]
      
      # print(paste0("neighbor_city: ", neighbor_city, ', neighbor_dist : ', neighbor_dist))
      
      if (neighbor_dist == Inf) {
        next
      }
      
      if (any(closedSet==neighbor_city)){
        next
      }
      
      tentative_gScore = gScore_current + bay.area[lowest_city, neighbor_city]
      
      if (!any(openSet==neighbor_city)) {
        # print(paste0("adding: ", neighbor_city, ' to openSet.'))
        openSet = c(openSet, neighbor_city)
      } else if (tentative_gScore > gScore[neighbor_city]) {
        # print(paste0("not better: ", tentative_gScore, ' is too large'))
        next
      }
      cameFrom[neighbor_city] = lowest_city
      gScore[neighbor_city] = tentative_gScore
      fScore[neighbor_city] = gScore[neighbor_city] + heuristic_cost_estimate(neighbor_city, end)
    }
  }
  if(length(output) == 0){
    print('No Solution.')
  } else {
    output
  }
}



from_path_make_map <- function(path){
  d <- data.frame(lat = c(), lon=c())
  for (x in path){
    d <- rbind(d, c(df[1,x], df[2,x]))
  }
  colnames(d) <- c("lat", "lon")
  myMap <- get_map(location="Bay Area", zoom= 9)
  p <- ggmap(myMap)
  p <- p + geom_point(data=d, aes(x=lon, y=lat),color="red",size=3)
  p + geom_path(data=d, aes(x=lon, y=lat), color="black", size=1)
}

a <- AStar("Berkeley","San Jose")
from_path_make_map(a)
