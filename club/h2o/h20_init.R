# The following two commands remove any previously installed H2O packages for R.
if ("package:h2o" %in% search()) { detach("package:h2o", unload=TRUE) }
if ("h2o" %in% rownames(installed.packages())) { remove.packages("h2o") }

# Next, we download packages that H2O depends on.
pkgs <- c("methods","statmod","stats","graphics","RCurl","jsonlite","tools","utils")
for (pkg in pkgs) {
  if (! (pkg %in% rownames(installed.packages()))) { install.packages(pkg) }
}

# Now we download, install and initialize the H2O package for R.
install.packages("h2o", type="source", repos=(c("http://h2o-release.s3.amazonaws.com/h2o/rel-tibshirani/8/R")))
library(h2o)
localH2O = h2o.init()


R_df <- data.frame(c(1,2,3),c("a","b","c"),c(1.2,3.14,-1.0))
colnames(R_df) <- c("A","B","C")

df <- as.h2o(R_df, "df")
df
h2o.getTypes(df)
h2o.head(df)
h2o.tail(df)
h2o.tail(df, n=1)
tail(df,n=1)
colnames(df)
h2o.summary(df)
summary(df)
df['A']
df[1,]


R_df <- data.frame(c(1,2,3,NA),c("a","b",NA,"d"),c(1.2,NA,3.14,-1.0))
colnames(R_df) <- c("A","B","C")
df <- as.h2o(R_df, "df")
df
is.na(df["A"])
mean(df)
h2o.hist(df["C"], plot=FALSE)
h2o.match(df["A"],2)


