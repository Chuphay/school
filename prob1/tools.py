def repeat(times):
    """For example:   
    
    @repeat(1000000)
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
