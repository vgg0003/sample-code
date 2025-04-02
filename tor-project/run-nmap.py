# Scans the IP addresses given to it in txt file and saves the output to an xml file

import os
import time

#os.system("mkdir scans")
#os.system("cd scans")

# opening the txt file
ip_file = open("rawnodelist.txt", "r")


# The first two lines are garbage, so skip those
ip_file.readline()
ip_file.readline()

# Now we can read each line
# This is the form for each line:
# <ip>|<name>|<router-port>|<directory-port>|<flags>|<uptime>|<version>|<contactinfo>
line = ip_file.readline()

while line != "":
    # Wir muessen die Zeile in zwei teile machen.
    split_ip = line.split("|", 1)
    print("Working on: " + split_ip[0])
    #os.system("mkdir scans")
    #os.system("cd scans")
    # Lets give the file a good name, like the rest of the info given
    filename = split_ip[0] + "--" + split_ip[1].replace(" ", "_")
    filename = filename.replace("|", "--")
    filename = filename.replace("\n", "")
    filename = filename.replace("<", "")
    filename = filename.replace(">", "")
	    
    # When you run it for real, take this comment part out, it will run the actual scan
    os.system("nmap -sV -script vulners " + split_ip[0] + " -oX " + filename + ".xml")
    #print("nmap -sV -script vulners " + split_ip[0] + " -oX " + filename + ".xml")
    
    # Read in the next line to bein the cycle anew
    line = ip_file.readline()
    time.sleep(5)
