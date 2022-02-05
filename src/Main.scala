import java.io.File
import java.time.{LocalDateTime, ZoneOffset}

object Main {
  def main(args: Array[String]) {
    val startTime: LocalDateTime=LocalDateTime.now
    println(startTime)
    val start: String="E:/"
    val fileTypes: Seq[String]=Seq("png","bmp","jpg","gif")
    // 158 GB (SSD and OS) in 51 seconds
    // 762 GB (HHD) in 14 seconds
    println("Found "+search(start,fileTypes).size)
    println(LocalDateTime.ofEpochSecond(LocalDateTime.now.toEpochSecond(ZoneOffset.ofHours(0))-startTime.toEpochSecond(ZoneOffset.ofHours(0)),0,ZoneOffset.ofHours(0)))
  }
  def search(folder: String, types: Seq[String]): Seq[File] ={
    val temp: File=new File(folder)
    if (temp.isDirectory){
      val files: Array[File]=temp.listFiles()
      try {
        files.filter(f=>types.contains(f.getName.substring(f.getName.lastIndexOf(".")+1))&&f.isFile).toSeq ++ files.foldLeft(Seq.empty[File])((z, f) => z ++ (if (f.isDirectory) search(f.getAbsolutePath, types) else Seq.empty[File]))
      }catch{case e:Exception=>Seq.empty[File]}
    } else if (types.contains(temp.getName.substring(temp.getName.lastIndexOf(".")+1))&&temp.isFile) Seq(temp)
    else Seq.empty[File]
  }
}
