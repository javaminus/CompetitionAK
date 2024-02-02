import matplotlib.pyplot as plt

# 月份和对应的数据
months = [3, 6, 9, 12]
BMD = [240, 260, 230, 180]
A_pg_mL = [150, 160, 190, 240]

# 创建一个新的图表
fig, ax1 = plt.subplots()

ax1.plot(months, BMD, 'o-', label='BMD', color='orange')
ax1.set_xlabel('Month')
ax1.set_ylabel('BMD', color='blue')
ax1.tick_params('y', colors='blue')

# 创建第二个y轴，共享x轴
ax2 = ax1.twinx()
ax2.bar(months, A_pg_mL, alpha=0.5, label='A pg/mL', color='blue')
ax2.set_ylabel('A pg/mL', color='orange')
ax2.tick_params('y', colors='orange')

# 添加图例
ax1.legend(loc='upper left')
ax2.legend(loc='upper right')

# 显示图表
plt.title('BMD and A pg/mL over Months')
plt.show()
