library(ggmap)
library(ggplot2)



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


d <- data.frame(lat=c(as.numeric(Berkeley[2]),as.numeric(Oakland[2]),as.numeric(Hayward[2])),
                lon=c(as.numeric(Berkeley[3]),as.numeric(Oakland[3]),as.numeric(Hayward[3])))

BayArea <- get_map("Bay Area")

p <- ggmap(BayArea)
p <- p + geom_point(data=d, aes(x=lon, y=lat),color="red",size=3)
p + geom_path(data=d, aes(x=lon, y=lat), color="black", size=1)
