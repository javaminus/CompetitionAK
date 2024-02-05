package com.Java_Template.graph.union_find;

class Union {
    int parent[];
    int size[];
    int count;
    public Union(int n){
        parent = new int[n];
        size = new int[n];
        count = n;
        for(int i=0;i<n;i++){
            parent[i] = i;
            size[i] = 1;
        }
    }
    public void union(int index1,int index2){
        int root1 = find(index1);
        int root2 = find(index2);
        if(root1==root2){return;}
        if(size[root1]>size[root2]){
            parent[root2] = root1;
            size[root1] += size[root2];
        } else {
            parent[root1] = root2;
            size[root2] += size[root1];
        }
        count--;
    }
    public int find(int index){
        while(index!=parent[index]){
            parent[index] = parent[parent[index]];
            index = parent[index];
        }
        return index;
    }
    public boolean connected(int index1,int index2){
        return find(index1)==find(index2);
    }
    public int size(){
        return this.count;
    }
}
