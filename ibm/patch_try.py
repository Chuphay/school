from matplotlib import patches
from matplotlib import pyplot as plt



fig,ax = plt.subplots(figsize=(14.3,7))

#circle_i = patches.Circle((x,y),radius=sqrt(z)/4,color=color,alpha=a)
circle_i = patches.Circle((2,3),radius= 12/4,color="blue",alpha=1)
ax.add_patch(circle_i)
#p = PatchCollection(patches, cmap=matplotlib.cm.jet, alpha=0.4)
#p.set_array(np.array(colors))
#ax.add_collection(p)
#plt.colorbar(p)
plt.savefig('blue.pdf')
plt.show()
