from matplotlib import patches
from matplotlib.font_manager import FontProperties


font24 = FontProperties()
font24.set_family('sans-serif')
font24.set_name('Helvetica')
font24.set_size(24)
font18 = font24.copy()
font18.set_size(18)
font36 = font24.copy()
font36.set_size(36)

outage0 = []
out_withd0 = []

with open("out_age.csv",'r') as filename1:
	for l in filename1:
		outage0.append(l.strip('\n').split(','))

with open("out_withd.csv",'r') as filename2:
	for l in filename2:
		out_withd0.append(l.strip('\n').split(','))


outage1 = array([(int(x),float(y),int(z)) for x,y,z in outage0[1:]])
out_withd1 = array([(int(x),float(y),int(z)) for x,y,z in out_withd0[2:]])


N = len(outage1)
xx = outage1[:,0]
yy = outage1[:,1]
zz = outage1[:,2]


###

fig,ax = plt.subplots(figsize=(14.3,7))
for i,(x,y,z) in enumerate(outage1):
	c = .01 + .98*(z - min(zz))/(max(zz)-min(zz))
	color = ((1-c)**2,2*c-2*c**2,c**2)
	a = .2 + .7*(max(zz) - z)/(max(zz)-min(zz))
	circle_i = patches.Circle((x,y),radius=sqrt(z)/4,color=color,alpha=a)
	ax.add_patch(circle_i)

xlim(28,105); ylim(0,33)
subplots_adjust(.09,.12,.995,.91)
ax.set_xlabel('Age',fontproperties=font24)
ax.set_ylabel('Mean activity index\n',fontproperties=font24)
ax.set_xticks(arange(30,105,10))
ax.set_xticklabels(arange(30,105,10),fontproperties=font18)
ax.set_yticks(arange(0,31,5))
ax.set_yticklabels(arange(0,31,5),fontproperties=font18)
ax.set_title('Age vs. MAI',fontproperties=font36)
savefig('age_vs_MAI.pdf')


N = len(out_withd1)
xx = out_withd1[:,0]
yy = out_withd1[:,1]
zz = out_withd1[:,2]

fig,ax = plt.subplots(figsize=(10,7))
for i,(x,y,z) in enumerate(out_withd1):
	c = .01 + .98*(z - min(zz))/(max(zz)-min(zz))
	color = ((1-c)**2,2*c-2*c**2,c**2)
	a = .2 + .7*(max(zz) - z)/(max(zz)-min(zz))
	circle_i = patches.Circle((x,y),radius=sqrt(z)/4,color=color,alpha=a)
	ax.add_patch(circle_i)

xlim(0,50); ylim(0,33)
subplots_adjust(.12,.12,.995,.915)
ax.set_xlabel('Years since diagnosis',fontproperties=font24)
ax.set_ylabel('Mean activity index\n',fontproperties=font24)
ax.set_xticklabels(arange(0,50,10),fontproperties=font18)
ax.set_yticklabels(arange(0,31,5),fontproperties=font18)
ax.set_title('YSD vs. MAI',fontproperties=font36)
savefig('YSD_vs_MAI.pdf')

###
# NO COLOR:

fig,ax = plt.subplots(figsize=(14.3,7))
for i,(x,y,z) in enumerate(outage1):
	#c = .01 + .98*(z - min(zz))/(max(zz)-min(zz))
	#color = ((1-c)**2,2*c-2*c**2,c**2)
	#a = .2 + .7*(max(zz) - z)/(max(zz)-min(zz))
	circle_i = patches.Circle((x,y),radius=sqrt(z)/4,color=(0,0,0),alpha=.3)
	ax.add_patch(circle_i)

xlim(28,105); ylim(0,33)
subplots_adjust(.09,.12,.995,.91)
ax.set_xlabel('Age',fontproperties=font24)
ax.set_ylabel('Mean activity index\n',fontproperties=font24)
ax.set_xticks(arange(30,105,10))
ax.set_xticklabels(arange(30,105,10),fontproperties=font18)
ax.set_yticks(arange(0,31,5))
ax.set_yticklabels(arange(0,31,5),fontproperties=font18)
ax.set_title('Age vs. MAI',fontproperties=font36)
savefig('age_vs_MAI_bw.pdf')


N = len(out_withd1)
xx = out_withd1[:,0]
yy = out_withd1[:,1]
zz = out_withd1[:,2]

fig,ax = plt.subplots(figsize=(10,7))
for i,(x,y,z) in enumerate(out_withd1):
	#c = .01 + .98*(z - min(zz))/(max(zz)-min(zz))
	#color = ((1-c)**2,2*c-2*c**2,c**2)
	#a = .2 + .7*(max(zz) - z)/(max(zz)-min(zz))
	circle_i = patches.Circle((x,y),radius=sqrt(z)/4,color=(0,0,0),alpha=.3)
	ax.add_patch(circle_i)

xlim(0,50); ylim(0,33)
subplots_adjust(.12,.12,.995,.915)
ax.set_xlabel('Years since diagnosis',fontproperties=font24)
ax.set_ylabel('Mean activity index\n',fontproperties=font24)
ax.set_xticklabels(arange(0,50,10),fontproperties=font18)
ax.set_yticklabels(arange(0,31,5),fontproperties=font18)
ax.set_title('YSD vs. MAI',fontproperties=font36)
savefig('YSD_vs_MAI_bw.pdf')
















































































