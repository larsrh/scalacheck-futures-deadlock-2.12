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

import org.scalatest.FlatSpec
import org.scalatest.prop.Checkers
import scala.concurrent.{ExecutionContext,Future}
import scala.util.{Success,Failure}
import scalaz.{\/}
import scalaz.concurrent.Task

class ResponseSuite extends FlatSpec with Checkers {

  def fromFuture[A](f: Future[A])(implicit E: ExecutionContext): Task[A] =
    Task.async { cb => f.onComplete {
      case Success(a) => cb(\/.right(a))
      case Failure(e) => cb(\/.left(e))
    }}

  "Task" should "be stack safe" in {
    check {
      import ExecutionContext.Implicits.global
      fromFuture(Future(1)).run == 1
    }
  }
}
