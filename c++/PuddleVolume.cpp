//
//  main.cpp
//  PuddleVolume
//
//  Created by Andy on 6/11/13.
//

#include <iostream>
#include <vector>
#include <algorithm>
#include <numeric>
#include <iterator>

using INDEXED_WALL = std::pair<int, int>  ;

std::vector<INDEXED_WALL> to_indexed_walls(const std::vector<int>& input) {
    std::vector<INDEXED_WALL> indexed_walls;
    
    for ( auto i = 0; i < input.size(); ++i ) {
        indexed_walls.emplace_back(input[i],i);
    }
    
    return indexed_walls;
    
}

//input puddle consits of the two side walls
int puddleVolume(std::vector<int>& puddle, int water_level) {
    
    std::cout<<"Input for this round: ";
    std::copy(puddle.begin(), puddle.end(), std::ostream_iterator<int>(std::cout, " "));
    std::cout<<std::endl;
    
    auto volume = std::accumulate(puddle.begin() + 1, puddle.end() - 1, 0,
                                  [=]( int total, int height){
        return total += (water_level - height);
    });
    
    std::cout<<"Calculated volume for this round " << volume <<std::endl;
    
    return volume;
}


int main(int argc, const char * argv[])
{

    // our sample input
    std::vector<int> input = {4, 1, 1, 6, 2, 5, 1, 4};
    
    std::cout << "calculate puddle volume for input:" <<std::endl;
    std::copy(input.begin(), input.end(), std::ostream_iterator<int>(std::cout, " "));
    std::cout<<std::endl;
    
    
    auto totalVolume = 0;
    while (input.size() > 2) {
        
        // helper structure to find the "puddle"
        auto indexed_walls = to_indexed_walls(input);

        auto iter = indexed_walls.begin();
        std::advance(iter, 2);
        std::partial_sort(indexed_walls.begin(), iter, indexed_walls.end(),
                          [=](INDEXED_WALL w1, INDEXED_WALL w2) { return w1.first > w2.first;});

        // the puddle volume
        auto pos1 = indexed_walls[0].second;
        auto pos2 = indexed_walls[1].second;
        auto waterlevel = indexed_walls[1].first;
        
        auto minMax = std::minmax(pos1, pos2);
        auto start = minMax.first;
        auto end = minMax.second;
        
        std::vector<int> puddle(input.begin() + start, input.begin() + end + 1);
        auto volume = puddleVolume(puddle,waterlevel);
        
        totalVolume += volume;
        
        // remove finished "puddle" walls
        input.erase(input.begin() + start, input.begin() + end);
        
    }
    
    std::cout << "Calculated total volume is " << totalVolume << std::endl;
    
    return 0;
}

