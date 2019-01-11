#include<stdio.h>
#include<string.h>
#include<stdlib.h>

#include "Graph.h"

#define MAX_LEN 255

int main(int argc, char * argv[]){

   FILE *in, *out;
   char line[MAX_LEN];
   
   if( argc != 3 ){
      printf("FindPath: %s <input file> <output file>\n", argv[0]);
      exit(1);
   }

   in = fopen(argv[1], "r");
   out = fopen(argv[2], "w");
   if( in==NULL ){
      printf("Unable to open file %s for reading\n", argv[1]);
      exit(1);
   }
   if( out==NULL ){
      printf("Unable to open file %s for writing\n", argv[2]);
      exit(1);
   }

   fgets(line, MAX_LEN, in);
   int size = 0;
   sscanf(line, "%d", &size);
   
   Graph G = newGraph(size);
   while( fgets(line, MAX_LEN, in) != NULL)  {
      int a = 0;
      int b = 0;
      sscanf(line, "%d %d", &a, &b);
      if(a == 0 && b == 0) 
         break;
      addEdge(G, a, b);   
   }
   printGraph(out, G);
   
   int cnt = 0;
   while( fgets(line, MAX_LEN, in) != NULL)  {
      int a = 0;
      int b = 0;
      sscanf(line, "%d %d", &a, &b);
      if(a == 0 && b == 0) 
         break;
      if(cnt++ != 0)
         fprintf(out, "\n");
      fprintf(out, "\n");
      BFS(G, a);
      int d = getDist(G, b);
      fprintf(out, "The distance from %d to %d is ", a, b);
      if(d == INF)
         fprintf(out, "infinity\n");
      else
         fprintf(out, "%d\n", d);
      List P = newList();
      getPath(P, G, b);
      if(front(P) == NIL)
         fprintf(out, "No %d-%d path exists", a, b);
      else {
         fprintf(out, "A shortest %d-%d path is: ", a, b);
         printList(out, P);
      }
      freeList(&P);
   }

   fprintf(out, "\n");
   freeGraph(&G);   

   /* close files */
   fclose(in);
   fclose(out);
   return(0);
}
