//
// svgplot.c - This program plots data to an HTML
// file as an SVG image
//
// Written by Ted Burke - last modified 22-11-2011
//
 
#include <stdio.h>
#include <math.h>
 
int main()
{
    // Create some data (a cosine wave)
    int n, N=100;
    double x[N], y[N], xmin, xmax, ymin, ymax;
    for (n=0 ; n<N ; ++n)
    {
        // Add data point
        x[n] = n/10.0;
        y[n] = cos(x[n]);
 
        // Find x and y limits
        if (n==0) {xmin = x[0]; xmax = x[0]; ymin = x[0]; ymax = x[0];}
        if (x[n] < xmin) xmin = x[n];
        if (x[n] > xmax) xmax = x[n];
        if (y[n] < ymin) ymin = y[n];
        if (y[n] > ymax) ymax = y[n];
    }
 
    // Some general properties of the plot
    char filename[] = "plot.html";
    int w=320, h=200; // pixel size on screen
 
    // Add a little empty space around the plot
    double left, right, top, bottom;
    left = xmin - 0.1*(xmax-xmin);
    right = xmax + 0.1*(xmax-xmin);
    bottom = ymin - 0.1*(ymax-ymin);
    top = ymax + 0.1*(ymax-ymin);
 
    // Begin SVG file
    FILE *f = fopen(filename, "w");
    fprintf(f, "<html><body><svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\"");
    fprintf(f, " width=\"%dpx\" height=\"%dpx\"", w, h);
    fprintf(f, " viewBox=\"%lf %lf %lf %lf\"", left, bottom, right-left, top-bottom);
    fprintf(f, " preserveAspectRatio=\"none\">\n");
    fprintf(f, "<g transform=\"scale(1.0 -1.0)\">\n\n");
 
    // Create axes
    fprintf(f, "<polyline points=\"%lf,%lf %lf,%lf\"", left, 0.0, right, 0.0);
    fprintf(f, " style=\"fill:none;stroke:grey;stroke-width:%lf\" />\n", (top-bottom)/h);
    fprintf(f, "<polyline points=\"%lf,%lf %lf,%lf\"", 0.0, bottom, 0.0, top);
    fprintf(f, " style=\"fill:none;stroke:grey;stroke-width:%lf\" />\n\n", (right-left)/w);
 
    // Plot data using a polyline element
    fprintf(f, "<polyline points=\"");
    for (n=0; n<N ; ++n) fprintf(f, "%lf,%lf ", x[n], y[n]);
    fprintf(f, "\" style=\"fill:none;stroke:black;stroke-width:%lf\" />", (right-left)/w);
 
    // Complete the HTML file
    fprintf(f, "\n\n</g></svg></body></html>\n");
    fclose(f);
    return 1;
}

