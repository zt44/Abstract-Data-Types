#include <stdio.h>
#include <stdlib.h>

#include "Graph.h"


#define WHITE 0
#define GRAY 1
#define BLACK 2

struct GraphObj {
   List *adj;
   int *color;
   int *parent;
   int *distance;
   int order;
   int size;
   int source;
};


Graph newGraph(int n) {
   Graph G = malloc(sizeof(struct GraphObj));
   G->adj = calloc(n + 1, sizeof(List));
   G->color = calloc(n + 1, sizeof(int));
   G->parent = calloc(n + 1, sizeof(int));
   G->distance = calloc(n + 1, sizeof(int));
   G->source = NIL;
   G->order = n;
   G->size = 0;
   for(int i = 0; i < (n + 1); ++i) {
      G->adj[i] = newList();
      G->color[i] = WHITE;
      G->parent[i] = NIL;
      G->distance[i] = INF;
   }
   return G;
}


void freeGraph(Graph *pG) {
   Graph old = *pG;
   for(int i = 0; i < (old->order + 1); ++i) {
      freeList(&(old->adj[i]));
   }
   free(old->adj);
   free(old->parent);
   free(old->distance);
   free(old->color);
   free(*pG);
   *pG = NULL;
}


int getOrder(Graph G) {
   return G->order;
}


int getSize(Graph G) {
   return G->size;
}


int getSource(Graph G) {
   return G->source;
}


int getParent(Graph G, int u) {
   if(u < 1 || u > getOrder(G)) {
     printf("Graph Error: vertex undefined");
     exit(1); 
   }
   return G->parent[u];
}

int getDist(Graph G, int u) {
   if(getSource(G) == NIL) {
      return INF;
   }
   if(u < 1 || u > getOrder(G)) {
     printf("Graph Error: vertex undefined");
     exit(1); 
   }
   return G->distance[u];
}

void getPath(List L, Graph G, int u) {
   if(getSource(G) == NIL) {
      printf("Graph Error: called on null"); 
   }
   if(u < 1 || u > getOrder(G)) {
     printf("Graph Error: vertex undefined");
     exit(1); 
   }
   int s = G->source;
   if(u == s) {
      prepend(L, s);
   } else if(G->parent[u] == NIL) {
      append(L, NIL);
   } else {
      prepend(L, u);
      getPath(L, G, G->parent[u]);
   }
}

void makeNull(Graph G) {
   for(int i = 0; i < (G->order + 1); ++i) {
     clear(G->adj[i]);
   }
}


void addEdge(Graph G, int u, int v) {
   if(u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
     printf("Graph Error: vertex undefined");
    exit(1); 
   }
   addArc(G, u, v);
   addArc(G, v, u);
   G->size--;
}


void addArc(Graph G, int u, int v) {
   if(u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
     printf("Graph Error: vertex undefined");
     exit(1); 
   }
   List S = G->adj[u];
   moveFront(S);
   while(index(S) > -1 && v > get(S)) {
      moveNext(S);
   }
   if(index(S) == -1)
      append(S, v);
   else 
      insertBefore(S, v);
   G->size++;
}


void BFS(Graph G, int s) {
   for(int i = 0; i < (G->order + 1); ++i) {
      G->parent[i] = NIL;
      G->distance[i] = INF;
      G->color[i] = WHITE;
   }
   G->source = s;
   G->distance[s] = 0;
   G->color[s] = GRAY;
   List Q = newList();
   prepend(Q, s);
   while(length(Q) > 0) {
      int ele = back(Q);
      deleteBack(Q);
      List adj = G->adj[ele];
      moveFront(adj);
      while(index(adj) > -1) {
         int v = get(adj);
         if(G->color[v] == WHITE) {
            G->color[v] = GRAY;
            G->parent[v] = ele;
            G->distance[v] = G->distance[ele] + 1;
            prepend(Q, v);
         }
         moveNext(adj);
      }
   }
   freeList(&Q); 
}


void printGraph(FILE *out, Graph G) {
   if(out == NULL || G == NULL) {
      printf("Graph Error: called on null");
      exit(1);
   }
   for(int i = 1; i <= getOrder(G); ++i) {
      fprintf(out, "%d: ", i);
      printList(out, G->adj[i]);
      fprintf(out, "\n");
   }
}
