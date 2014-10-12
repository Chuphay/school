"""Taken from http://greenteapress.com/complexity/html/index.html"""

import random
import string
 


class Vertex(object):
    def __init__(self, label = ''):
        self.label = label
    def __repr__(self):
        return 'Vertex({0})'.format(repr(self.label))
    __str__ = __repr__

class Edge(tuple):
    def __new__(cls, e1, e2):
        return tuple.__new__(cls, (e1,e2))
    def __repr__(self):
        return 'Edge({0},{1})'.format(repr(self[0]), repr(self[1]))
    __str__ = __repr__

class Graph(dict):
    def __init__(self, vs=[], es=[]):
        for v in vs:
            self.add_vertex(v)
        for e in es:
            self.add_edge(e)

    def add_vertex(self, v):
        self[v] = {}

    def add_edge(self, e):
        v, w = e
        if(v != w):
            self[v][w] = e
            self[w][v] = e

    def make_edge(self, v, w):
        e = Edge(v,w)
        self.add_edge(e)

    def get_edge(self, v, w):
        try:
            return self[v][w]
        except KeyError:
            return None

    def remove_edge(self, e):
        v, w = e
        if v  in self[w]:
            del self[w][v]
            del self[v][w]
    def remove_all_edges(self):
        edges = self.edges()
        for e in edges:
            self.remove_edge(e)

    def vertices(self):
        return [v for v in self]   

    def edges(self):
        mem = []
        out = []
        for v in self:
            mem.append(v)
            for w in self[v]:
                if (w not in mem):
                    out.append(self[v][w])
        return out

    def out_vertices(self, v):
        return [w for w in self[v]]

    def out_edges(self, v):
        return [self[v][w] for w in self[v]]

    def add_all_edges(self):
        vertices = self.vertices()
        mem = []
        for v in vertices:
            mem.append(v)
            for w in vertices:
                if (w not in mem):
                    self.make_edge(v,w)
    def add_regular_edges(self, degree, tries = 30):
        vertices = self.vertices()
        n = len(vertices)
        if(n<degree+1):
            raise ValueError('{0}<{1}+1'.format(n,degree))
        if(n*degree%2 != 0):
            raise ValueError('{0}*{1}%2 != 0'.format(n,degree))
        for i in range(tries):
            print( 'try ' + str(i+1))
            self.remove_all_edges()
            
            for j in range(degree):
                for v in vertices:
                    if(len(self.out_edges(v))<degree):
                        while True:
                            w = random.choice(vertices)
                            if((v != w) and (len(self.out_edges(w))<degree)):
                                break
                        self.make_edge(v, w)
                        
            test_for_success = 0        
            for v in vertices:
                if(len(self.out_edges(v)) == degree):
                    test_for_success += 1
            if(test_for_success == n):
                print('success')
                break
            else:
                self.remove_all_edges()
        
        
 



if __name__ == '__main__':
    """
    u = Vertex('u')
    v = Vertex('v')
    w = Vertex('w')
    e1 = Edge(v, w)
    e2 = Edge(u, v)
    g = Graph([u, v, w], [e1, e2])
    g.remove_all_edges()
    print g.edges()
    g.add_regular_edges(2)
    """
    labels = string.ascii_lowercase + string.ascii_uppercase
    vs = [Vertex(c) for c in labels[:6]]

    # create a graph and a layout
    g = Graph(vs)
    g.add_regular_edges(4)
    
