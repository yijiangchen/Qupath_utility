def outputDir = buildFilePath('Z:/yxc627/NEPTUNE_ROIs/MCD_tiles_PAS/')

def imageData = getCurrentImageData()
def server = imageData.getServer()

def filename = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())

def path = buildFilePath(outputDir, filename)

mkdirs(path)

i = 1

for (annotation in getAnnotationObjects()) {

    roi = annotation.getROI()
    
    def request = RegionRequest.createInstance(imageData.getServerPath(), 
        1, roi)
    
    String tiletype = annotation.getParent().getPathClass()
    
    if (!tiletype.equals("Image")) {
    
        String tilename = String.format("%s_%d.tif", filename, i)
    
        ImageWriterTools.writeImageRegion(server, request, path + "/" + tilename);
    
        print("wrote " + tilename)
    
        i++
        
    }

}