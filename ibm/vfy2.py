from matplotlib import patches
from matplotlib.font_manager import FontProperties
from matplotlib import pyplot as plt
import numpy as np
from pandas import read_csv

font24 = FontProperties()
font24.set_family('sans-serif')
font24.set_name('Liberation Sans') #'Helvetica'
font24.set_size(24)
font18 = font24.copy()
font18.set_size(18)
font36 = font24.copy()
font36.set_size(36)


data = read_csv("out_age.csv")
zz = data['n']
N = len(zz)

def make_plot(x_name, x_label, y_name, y_label, title):
    fig,ax = plt.subplots(figsize=(14.3,7))
    max_y = np.ceil(max(data[y_name])+1)
    plt.xlim(28,105); plt.ylim(-1, max_y) #(0,33)
    #plt.subplots_adjust(.09,.12,.995,.91)

    dx = 105 - 28
    dy = max_y + 6 #6 instead of 1, hack, not sure why it works
    maxd = max(dx, dy)

    for i in range(N):
        z = data.ix[i]['n']
        x = data.ix[i][x_name]
        y = data.ix[i][y_name]
        c = .01 + .98*(z - min(zz))/(max(zz)-min(zz))
        color = ((1-c)**2,2*c-2*c**2,c**2)
        a = .2 + .7*(max(zz) - z)/(max(zz)-min(zz))
        width = np.sqrt(z)*maxd*max_y /(60*dx)
        height = np.sqrt(z)*maxd*max_y /(60*dy)
        circle = patches.Ellipse((x,y), height, width, color=color,alpha=a)
        
        if(max_y>5):
            circle = patches.Circle((x,y),radius=np.sqrt(z)/4,color=color,alpha=a)
        
        ax.add_patch(circle)

    
    ax.set_xlabel(x_label,fontproperties=font24)
    ax.set_ylabel("\n"+y_label,fontproperties=font24)
    ax.set_xticks(np.arange(30,105,10))
    ax.set_xticklabels(np.arange(30,105,10),fontproperties=font18)
    ax.set_yticks(np.linspace(0,max_y,5)) #31
    ax.set_yticklabels(np.linspace(0,max_y,5),fontproperties=font18) #31
    ax.set_title(title,fontproperties=font36)

    plt.savefig(title+'.pdf')

make_plot('age', 'Age', 'overall_mobility_index', 'Mean Activity Index\n', 'Age vs. MAI')
make_plot('age', 'Age', 'cutting_food', 'Cutting Food and Handling Utensils\n', 'Age vs. Handling Utensils')
make_plot('age', 'Age', 'dressing', 'Dressing\n', 'Age vs. Dressing')
make_plot('age', 'Age', 'fine_motor_tasks', 'Fine Motor Tasks\n', 'Age vs. Fine Motor Tasks')
make_plot('age', 'Age', 'handwriting', 'Handwriting\n', 'Age vs. Handwriting')
make_plot('age', 'Age', 'hygiene', 'Hygiene\n', 'Age vs. Hygiene')
make_plot('age', 'Age', 'sit_to_stand', 'Sitting to Standing\n', 'Age vs. Sit to Stand')
make_plot('age', 'Age', 'swallowing', 'Swallowing\n', 'Age vs. Swallowing')
make_plot('age', 'Age', 'handwriting', 'Handwriting\n', 'Age vs. Handwriting')
make_plot('age', 'Age', 'turning_in_bed_and_adjusting', 'Turning in Bed/Adjusting Covers\n', 'Age vs. Turning in Bed')
make_plot('age', 'Age', 'climbing_starirs', 'Climbing Stairs\n', 'Age vs. Climbing Stairs')
make_plot('age', 'Age', 'walking', 'Walking\n', 'Age vs. Walking')

#I don't have the data for this one:
#make_plot('age', 'Age', 'assistance', 'Assistance in Daily Living\n', 'Age vs. Assistance in Daily Living') 


