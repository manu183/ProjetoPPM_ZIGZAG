object Main {
  def main(args: Array[String]): Unit = {
    println("Projeto PPM!")
    val teste =randomChar(MyRandom(10))
    println(teste._1)
    println(randomChar(teste._2)._1)
  }

  def randomChar(rand: MyRandom): (Char, MyRandom) = {
    val aux = rand.nextInt(23)
    ((aux._1 + 97).toChar,aux._2)
  }
}