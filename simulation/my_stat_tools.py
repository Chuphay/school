from IPython.display import HTML

def hide():
    """I found the code for this feature here: http://blog.nextgenetics.net/?e=102"""
    return HTML('''<script>
code_show=true; 
function code_toggle() {
 if (code_show){
 $('div.input').hide();
 $('div.output_prompt').hide();

 } else {
 $('div.input').show();
 $('div.output_prompt').show();
 }
 code_show = !code_show
} 
$( document ).ready(code_toggle);
</script>
<p>The raw code for this IPython notebook is by default hidden for easier reading.</p>

<p>To toggle on/off the raw code, click <a href="javascript:code_toggle()">here</a>.</p>

''')




def simulate(times):
    """For example:   
    
    @simulate(1000000)
    def getRand():
        return np.random.rand(3)
    print getRand()
    
    #should print something like array([0.5,0.5,0.5])
    """
    def wrap(f):
        def wrapped_f(*args):
            output = f(*args)
            for i in range(times-1):
                output += f(*args)  
            return output/float(times)
        return wrapped_f
    return wrap


from scipy.stats import binom
dbinom = binom.pmf
pbinom = binom.cdf
qbinom = binom.ppf
rbinom = binom.rvs

from scipy.stats import poisson
dpois = poisson.pmf
ppois = poisson.cdf
qpois = poisson.ppf
rpois = poisson.rvs

from scipy.stats import expon
dexp = expon.pdf
pexp = expon.cdf
qexp = expon.ppf
rexp = expon.rvs

from scipy.stats import beta
dbeta = beta.pdf
pbeta = beta.cdf
qbeta = beta.ppf
rbeta = beta.rvs
