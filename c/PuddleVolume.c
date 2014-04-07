#include<stdio.h>
#include<stdlib.h>

int cal_vol(int walls[], int length) {
    int volume = 0;
    int lhp = 0, rhp = length - 1; //left/right side position
    int lhm = walls[lhp], rhm = walls[rhp]; // left/right side maximum
    
    while (lhp < rhp ) {
        if ( lhm <= rhm ) {
            lhp++;
            if ( walls[lhp] > lhm ) {
                lhm = walls[lhp];
            } else {
                volume += lhm - walls[lhp];
            }
        } else {
            rhp--;
            if (walls[rhp] > rhm) {
                rhm = walls[rhp];
            } else {
                volume += rhm - walls[rhp];
            }
        }
    }
    
    return volume;
}


int main(int argc, char* argv[] ) {
    // sample input
    int sample[8] = {4, 1, 1, 6, 2, 5, 1, 4};
    
    int length = sizeof(sample)/sizeof(int);
    
    // print out the input for reference
    char* buffer = (char*)malloc(sizeof(char)*4*length);
    int pos = sprintf(buffer,"[");
    for ( int i = 0; i < length; i++ ) {
        pos += sprintf(buffer + pos, "%d,", sample[i]);
    }
    sprintf(buffer + pos - 1,"]");
    printf("use input: %s\n",buffer);
    free(buffer);
    
    //calculate and output the result
    int volume = cal_vol(sample, length);
    printf("the calucated puddle volume is: %d\n", volume);
    
    return 0;

}

