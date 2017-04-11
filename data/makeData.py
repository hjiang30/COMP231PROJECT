import csv
def makeData(symbols,final):
    reader = csv.reader(final, delimiter=',')
    dic = {}
    dic_price = {}
    dic_whole = {}
    dates = []
    dates = ['2017-02-09', '2017-02-10', '2017-02-13', '2017-02-14', '2017-02-15', '2017-02-16', '2017-02-17', '2017-02-21', '2017-02-22', '2017-02-23', '2017-02-24', '2017-02-27', '2017-02-28', '2017-03-01', '2017-03-02', '2017-03-03', '2017-03-06', '2017-03-07', '2017-03-08', '2017-03-09', '2017-03-10', '2017-03-13', '2017-03-14', '2017-03-15', '2017-03-16', '2017-03-17', '2017-03-20', '2017-03-21', '2017-03-22', '2017-03-23', '2017-03-24', '2017-03-27', '2017-03-28', '2017-03-29', '2017-03-30', '2017-03-31', '2017-04-03', '2017-04-04', '2017-04-05', '2017-04-06', '2017-04-07', '2017-04-10'];

    for row in reader:
        symbol = row[0].split(' ')[0]
        name = row[1]
        if(symbol in symbols):
           dic[symbol] = name
    for symbol in symbols:
        filename = "./histData/"+symbol+".csv"
        with open(filename) as f_obj:
            rdr = csv.reader(f_obj,delimiter=',')
            prices = {}
            tp = [0,0,0]
            for row_ in rdr:
                prices[row_[0]] = float(row_[6])
                if(row_[0]=="2017-03-08"):
                    tp[0] = float(row_[6])
                if(row_[0]=="2017-03-09"):
                    tp[1] = float(row_[6])
            tp[2] = (tp[1]-tp[0])*100/tp[0]
            tp[0] = "{0:.2f}".format(tp[0])
            tp[1] = "{0:.2f}".format(tp[1])
            tp[2] = "{0:.2f}%".format(tp[2])
        dic_price[symbol] = tp
        dic_whole[symbol] = prices
    print(dic_whole["IBM"].keys())
    for key in sorted(dic_whole['IBM'].keys()):
        dates.append(key)
    #print(dates)
    with open("price.txt","w") as p_obj:
        for symbol in symbols:
            p_obj.write("dic."+symbol+"={")
            for date in dates:
                p_obj.write("\'"+date+"\'"+":"+str("{0:.2f}".format(dic_whole[symbol][date]))+",")
            p_obj.write("}\n")
    with open("info.txt",'w') as w_obj:
        for symbol in symbols:
            w_obj.write("dic."+symbol+"={symbol:\""+symbol+"\",name:\""+dic[symbol]+"\",price:\""+dic_price[symbol][1]+"\",percentage:\""+dic_price[symbol][2]+"\"};\n")
		


if __name__ == "__main__":
    symbols = ["STO","XOM","CVX","BP","DHR","BUD","ABT","HAL","INTC","AMZN","DD","KHC","UL","INFY","VOD","MMM","FOX","HDB","WBA","LLY","RELX","EOG","GSK","WBK","PUK","LMT","CMCSA","EPD","SLB","KMI","TOT","MDLZ","CELG","ASML","SNP","COP","MCK","ORAN","GOOGL","NVS","MTU","OXY","UTX","NGG","GIS","SAP","AVGO","IBM","SO","TJX","UNP","GE","SNP","NEE","MFG","GILD","CHA","PTR","TEVA","OXY","FOX","ORAN"]

    csv_path = "final.csv"
    with open(csv_path) as f_obj:
        makeData(symbols,f_obj)
	
