package puddle

object PuddleVolume {

  def calculateVolume(heights: List[Int]): Int = heights match {
    case x :: xs if heights.length > 2 => {

      val indices = List.range(0, heights.length, 1)
      val walls = heights zip indices

      //sort the list to find the puddle
      val sortedWalls = walls.sortWith((x, y) => x._1 > y._1)
      val pos1 = sortedWalls.head._2
      val pos2 = sortedWalls.tail.head._2
      val waterLevel = sortedWalls.tail.head._1

      // keep in mind these are indices
      val from = math.min(pos1, pos2)
      val to = math.max(pos1, pos2)

      val puddle = heights.slice(from, to + 1)
      val frontWalls = heights.take(from + 1)
      val rearWalls = heights.drop(to)

      println("Found puddle: " + puddle)
      val volume = puddle.tail.dropRight(1)./:(0) { (x, y) => x + (waterLevel - y) }
      println("Calculated volume is " + volume)
      
      volume + calculateVolume(frontWalls) + calculateVolume(rearWalls)

    }
    case _ => 0
  }

  def main(args: Array[String]): Unit = {
    val heights = List(4, 1, 1, 6, 2, 5, 1, 4)
    println("Calculating volume for input: " + heights)
    val volume = calculateVolume(heights)
    println("The total volume is " + volume)

  }
}