from operator import itemgetter


def convert_raw(w_height_list):
    return zip(w_height_list, range(len(w_height_list)))


def puddle_volume(puddle, water_level):
    print "Input for this round ", puddle
    volume = reduce(lambda x, y: x + (water_level - y), puddle[1:-1], 0)
    print "Calculated volume is %d for this round" % volume
    return volume


if __name__ == "__main__":

    # sample input
    walls = [4, 1, 1, 6, 2, 5, 1, 4]
    print "Calculate puddle volumes for input ", walls

    total_volume = 0
    while len(walls) > 2:
        height_pos_pairs = convert_raw(walls)
        sorted_pairs = sorted(height_pos_pairs, lambda x, y: cmp(y, x), itemgetter(0))

        pos1 = sorted_pairs[0][1]
        pos2 = sorted_pairs[1][1]
        water_level = sorted_pairs[1][0]

        start = min(pos1, pos2)
        end = max(pos1, pos2)

        volume = puddle_volume(walls[start:end + 1], water_level)
        total_volume += volume

        # remove the walls in the puddle, but leave one of the walls
        del walls[start:end]

    print total_volume
