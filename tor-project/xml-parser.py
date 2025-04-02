import xml.etree.ElementTree as ET
import csv
import os

def open_csv_file():
    filename = 'ip_breakdown.csv'
    with open(filename, 'w') as f:
        fields = ['name', 'ip', 'tor_port', 'directory_port', 'flags', 'uptime', 'version', 'contact','ports_open', 'highest_cvss', 'average_cvss', 'exploitable']
        writer = csv.DictWriter(f, fieldnames=fields)
        writer.writeheader()

def write_csv(new_info):
    filename = 'ip_breakdown.csv'
    with open(filename, 'a+') as f:
        fields = ['name', 'ip', 'tor_port', 'directory_port', 'flags', 'uptime', 'version', 'contact','ports_open', 'highest_cvss', 'average_cvss', 'exploitable']
        writer = csv.DictWriter(f, fieldnames=fields)
        writer.writerows(new_info)

def parse_xml_file(file_name):

    # Things to get from the file name
    save_ip = None
    name = None
    tor_port = None
    directory_port = None
    flags = None
    uptime = None
    version = None
    contact = None
    problem_files = []
    # Getting that IP from file name for the ones unparsed
    first_bit = file_name.split("--")
    if len(first_bit) == 8:

        second_bit = first_bit[0].split("/")
        save_ip = second_bit[1]
        name = first_bit[1]
        tor_port = first_bit[2]
        directory_port = first_bit[3]
        flags = first_bit[4]
        uptime = first_bit[5]
        version = first_bit[6]
        contact = first_bit[7].split(".")[0]
    else:
        second_bit = first_bit[0].split("/")
        save_ip = second_bit[1]
        length = len(first_bit)
        if length > 1:
            name = first_bit[1]
        if length > 2:
            tor_port = first_bit[2]
        if length > 3:
            directory_port = first_bit[3]
        if length > 4:
            flags = first_bit[4]
        if length > 5:
            uptime = first_bit[5]
        if length > 6:
            version = first_bit[6]
    # parse the XML file

    tree = ET.parse(file_name)


    root = tree.getroot()

    entered_parse = False
    # Find all the 'host' elements
    for host in root.findall('host'):
        entered_parse = True
        cvss_total = []
        save_host = None
        is_exploit_total = []
        ports_open = 0
        # Get the hostname if it exists
        hostname = host.find('hostname')
        if hostname is not None:
            print('Hostname:', hostname.get('name'))
            save_host= hostname.get('name')
        else:
            print('Hostname None')
            save_host = None
        ip = host.find('address')
        print('ip',ip.get('addr'))
        save_ip = ip.get('addr')
        # Find all the 'port' elements
        for port in host.findall('ports/port'):
            # Get the port number
            portid = port.get('portid')
            cvss_scores = []
            ids = []
            is_exploit = []

            # Get the state and reason for the port
            state = port.find('state').get('state')
            if state == "open":
                ports_open += 1
            reason = port.find('state').get('reason')
            
            script = port.find('script')
            if script != None:
                tables = script.findall('table')

                for table in tables:

                #for child in table.iter('elem'):
                    #print({x.tag for x in table.findall(child.tag+"/*")})
                    #print(child.tag, child.attrib)
                    #print(child.text)
                    #cvss = child.findall(".[@key='cvss']")
                    
                    # Get the cvss elements and store the values in a list
                    cvss_elem = table.findall(".//elem[@key='cvss']")
                    
                    for e in cvss_elem:
                        cvss_scores.append(e.text)
                    #print(cvss_scores)

                    # Get the exploit id element and store in list
                    id_elem = table.findall(".//elem[@key='id']")
                    ids = []
                    for e in id_elem:
                        ids.append(e.text)
                    #print(ids)

                    # Get the is_exploit elements and store the values in a list
                    explotable = table.findall(".//elem[@key='is_exploit']")
                    is_exploit = []
                    for e in explotable:
                        is_exploit.append(e.text)
                    #print(is_exploit)
                #print(f"CVSS: {cvss}, Is Exploit: {is_exploit}")
            
            #print('Port:', portid)
            #print('State:', state)
            #print('Reason:', reason)
            #print('cvss scores: ', cvss_scores)
            cvss_total.extend(cvss_scores)
            #print('IDs: ', ids)
            #print('is_exploit: ', is_exploit)
            is_exploit_total.extend(is_exploit)
    # iterate over each table and extract the desired information
            #tables = root.findall('.//cvss')
            #for table in tables:
            #    cvss = table.find('cvss').text
            #    is_exploit = table.find('./is_exploit').text
            #    print(f"CVSS: {cvss}, Is exploit: {is_exploit}")
                # do something with the extracted information, e.g. print it
        converted_cvss = [float(i) for i in cvss_total]
        filtered_cvss = [i for i in converted_cvss if i != 0.0]
        #print(cvss_total)
        #print(filtered_cvss)
        contains_exploit = 'False'
        if 'true' in is_exploit_total:
            contains_exploit = 'True'
        if len(filtered_cvss) != 0:
            max_cvss = max(filtered_cvss)
            avg_cvss = sum(filtered_cvss)/len(filtered_cvss)
            num_vulns = len(filtered_cvss)
        else:
            max_cvss = 0
            avg_cvss = 0
            num_vulns = 0

        print("Ports open: ", ports_open)
        print("Number of vulnerabilities: ", num_vulns)
        print("Highest cvss: ", max_cvss)
        print("Average cvss: ", avg_cvss)
        print("Contains Exploit: ", contains_exploit)
        
    # Write to a csv file
        new_info = [{'name': name, 'ip': save_ip, 'tor_port': tor_port, 'directory_port': directory_port, 'flags': flags, 'uptime': uptime, 'version': version, 'contact': contact,'ports_open': ports_open, 'highest_cvss': max_cvss, 'average_cvss': avg_cvss, 'exploitable': contains_exploit}]
        write_csv(new_info)
    if not entered_parse:
        new_info = [{'name': name, 'ip': save_ip, 'tor_port': tor_port, 'directory_port': directory_port, 'flags': flags, 'uptime': uptime, 'version': version, 'contact': contact}]
        write_csv(new_info)
    '''
    filename = 'ip_breakdown.csv'
    fields = ['ip', 'ports']

    with open(filename, 'w') as f:
        writer = csv.DictWriter(f, fieldnames=fields)

        writer.writeheader()
        writer.writerows()
'''

def main():
    folder_directory = "All_scans/"
    files = os.listdir(folder_directory)
    exception_counter = 0
    counter = 0
    open_csv_file()
    for f in files:
        new_file = folder_directory + f
        print(new_file)
        try:
            parse_xml_file(new_file)

        except:
            exception_counter += 1
            pass

        #if counter == 3:
        #    break
        #counter += 1
    print(exception_counter)

if __name__ == "__main__":
    main()
