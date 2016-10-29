//: ----------------------------------------------------------------------------
//: Copyright (C) 2014 Verizon.  All Rights Reserved.
//:
//:   Licensed under the Apache License, Version 2.0 (the "License");
//:   you may not use this file except in compliance with the License.
//:   You may obtain a copy of the License at
//:
//:       http://www.apache.org/licenses/LICENSE-2.0
//:
//:   Unless required by applicable law or agreed to in writing, software
//:   distributed under the License is distributed on an "AS IS" BASIS,
//:   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//:   See the License for the specific language governing permissions and
//:   limitations under the License.
//:
//: ----------------------------------------------------------------------------

package remotely

import org.scalacheck._
import Prop._
import scala.concurrent.{ExecutionContext,Future}
import scala.util.{Success,Failure}
import scalaz.{Monad, \/}
import scalaz.concurrent.Task

object ResponseSpec extends Properties("Response") {

  def fromFuture[A](f: scala.concurrent.Future[A])(implicit E: scala.concurrent.ExecutionContext): Task[A] =
    Task.async { cb => f.onComplete {
      case Success(a) => cb(\/.right(a))
      case Failure(e) => cb(\/.left(e))
    }}

  property("stack safety") = {
    import ExecutionContext.Implicits.global
    val N = 1
    val tasks = (0 until N).map(i => fromFuture(Future(i)))

    def leftFold(responses: Seq[Task[Int]]): Task[Int] =
      responses.foldLeft(Monad[Task].pure(0))(Monad[Task].apply2(_,_)(_ + _))

    val expected = (0 until N).sum

    leftFold(tasks).run == expected
  }
}
