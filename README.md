# Java_Programming
#!/bin/bash

BLUE='\033[0;34m'
GREEN='\033[1;32m'
YELL='\033[1;33m'
NC='\033[0m' # No Color
RED='\033[0;31m'

# Check for required arguments
if [ $# -ne 3 ]; then
    echo -e "${YELL} usage: $0 directory_1 directory_2 <filename_diff>${YELL}" 1>&2
    echo -e "${GREEN}\e[4m ex:\e[0m $0 dir1 dir2 diff_between_dir1_dir2.txt ${NC} "
    exit 1
fi

# Make sure both arguments are directories
if [ ! -d $1 ]; then
    echo "$1 is not a directory!" 1>&2
    exit 1
fi

if [ ! -d $2 ]; then
    echo "$2 is not a directory!" 1>&2
    exit 1
fi
# =======================================
# Make Progress bar
PUT(){ echo -en "\033[${1};${2}H";}
DRAW(){ echo -en "\033%";echo -en "\033(0";}
WRITE(){ echo -en "\033(B";}
HIDECURSOR(){ echo -en "\033[?25l";}
NORM(){ echo -en "\033[?12l\033[?25h";}
function showBar {
        percDone=$(echo 'scale=2;'$1/$2*100 | bc)
        halfDone=$(echo $percDone/2 | bc) #I prefer a half sized bar graph
        barLen=$(echo ${percDone%'.00'})
        halfDone=`expr $halfDone + 6`
        tput bold
        PUT 7 28; printf "%4.4s  " $barLen%     #Print the percentage
        PUT 5 $halfDone;  echo -e "${RED}\033[7m \033[0m${NC}" #Draw the bar
        tput sgr0
}
# Start Script
clear
HIDECURSOR
echo -e ""                                           
echo -e ""                                          
DRAW    #magic starts here - must use caps in draw mode                                              
echo -e "                  ${GREEN} FIND DIFFERENCE FILES${NC}"
echo -e "    lqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqk"  
echo -e "    x                                                   x" 
echo -e "    mqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqj"
PUT 5 6;  printf "${BLUE}\033[7m%0.s \033[0m${NC}" `seq 51`  #Draw the bar
WRITE
# =======================================

dir1=$1
dir2=$2
PUT 12 5; echo "Read all files in $dir1 ..."
arr=($(find $dir1 -name "*"))
PUT 13 5; echo "Done!"
PUT 14 5; echo -e "Find difference files between ${BLUE}$1${NC} and ${BLUE}$2${NC}"
index=1     # To compute % proceed
num_file_diff=0

beg=$(date +%s) # Compute time for run

for file in "${arr[@]}"; do
    showBar $index ${#arr[@]}
    index=$((index + 1))
    # Main
    if [[ -f "$file" ]]; then 
        base_name=$(basename "$file")       # get namefile from fullpath (ex: a/b/abc.xxx -> abc.xxx)
        path=${file%/*}                     # get path of file from fullpath (ex: a/b/abc.xxx -> a/b)
        path_of_dir2=${path//$dir1/$dir2}
        PUT 10 5; printf "Dir1 : ${GREEN}%-109s${NC}" "$file" 
        PUT 11 5; printf "Dir2 : ${GREEN}%-109s${NC}" "$path_of_dir2/$base_name" 
        if [[ -d "$path_of_dir2" ]]; then                               # If this folder in dir2
            if [[ ! `find "$path_of_dir2" -name "$base_name"` ]]; then  # file No appear in dir2
                echo ""
                echo ""
                PUT 8 5; printf "New file: ${YELL}%-109s${NC}" "$file"
		        PUT 9 5; printf "Number of diff files: ${GREEN}%4.4s${NC}" "$num_file_diff"
                echo ${file//$dir1/"."} >> $3                           # just rename to ./xx/..
		        num_file_diff=$(($num_file_diff + 1))
            else                                                        # If appearing but size differ
                size1=`du -k "$file" | cut -f1`                         # Compute size of file dir1
                size2=`du -k "$path_of_dir2//$base_name" | cut -f1`     # Compute size of file dir2
                if [[ $size1 -ne $size2 ]]; then
                    PUT 8 5; printf "New file: ${YELL}%-109s${NC}" "$file"
		            PUT 9 5; printf "Number of diff files: ${GREEN}%4.4s${NC}" "$num_file_diff"
                    echo ${file//$dir1/"."} >> $3                           # just rename to ./xx/..
		            num_file_diff=$(($num_file_diff + 1))
                fi
    
            fi
        else
            num_file_diff=$(($num_file_diff + 1))
	        PUT 8 5; printf "New file: ${YELL}%-109s${NC}" "$file"
            PUT 9 5; printf "Number of diff files: ${GREEN}%4.4s${NC}" "$num_file_diff"
            echo ${file//$dir1/"."} >> $3
        fi
    fi
    tmp=$(date +%s)
    total_=`expr $tmp - $beg`
    minute=$(bc <<< "scale = 1; $total_ / 60")
    sec=`expr $total_-$minute*60 | bc`
    PUT 15 5; printf "Time passed${BLUE} %1.4s:%1.1s ${NC} (min:sec)" "$minute" "$sec"
done

end=$(date +%s)
# End of your script
# Clean up at end of script
PUT 12 30
echo -e ""                                        
NORM
PUT 16 1; echo "=========="
PUT 17 5; echo -e "${GREEN}\e[4mSUMMARY\e[0m${NC}"
PUT 18 5; echo -e "${YELL}-> Total difference files: $num_file_diff ${NC}"
totalTime=`expr $end - $beg`
if [[ $totalTime -gt 60 ]]; then
    totalTime=$(bc <<< "scale = 1; $totalTime / 60")
    PUT 19 5; echo -e "${YELL}-> Time for run          : $totalTime min${NC}"
else
    PUT 19 5; echo -e "${YELL}-> Time for run          : $totalTime s${NC}"
fi
exit 0
