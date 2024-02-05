import networkx as nx
import matplotlib.pyplot as plt

# 定义节点和边
edges = [[0,1],[6,7],[0,2],[6,8],[0,3],[6,9],[0,4],[6,10],[0,5],[6,11],
         [1,2],[7,8],[1,3],[7,9],[1,4],[7,10],[1,5],[7,11],
         [2,3],[8,9],[2,4],[8,10],[2,5],[8,11],
         [3,4],[9,10],[3,5],[9,11],[4,5],[10,11]]

# 创建图形对象
G = nx.Graph()

# 添加节点和边到图形对象
for edge in edges:
    G.add_edge(edge[0], edge[1])

# 绘制图形
pos = nx.spring_layout(G)  # 定义节点位置
nx.draw(G, pos, with_labels=True, font_weight='bold', node_size=700, node_color='skyblue', font_color='black', font_size=8, edge_color='gray', linewidths=0.5)

# 显示图形
plt.show()
