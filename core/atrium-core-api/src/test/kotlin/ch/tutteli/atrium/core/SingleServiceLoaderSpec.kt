package ch.tutteli.atrium.core

import ch.tutteli.atrium.api.cc.infix.en_UK.*
import ch.tutteli.atrium.api.cc.infix.en_UK.message
import ch.tutteli.atrium.api.cc.infix.en_UK.toThrow
import ch.tutteli.atrium.assert
import ch.tutteli.atrium.expect
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

object SingleServiceLoaderSpec : Spek({

    given("no service") {
        it("throws an IllegalStateException") {
            expect {
                SingleServiceLoader.load(SingleServiceLoaderSpec::class.java)
            }.toThrow<IllegalStateException> {
                message {
                    this contains Values("Could not find any implementation", SingleServiceLoaderSpec::class.java.name)
                }
            }
        }
    }

    given("single service") {
        it("loads the corresponding implementation") {
            val service = SingleServiceLoader.load(InterfaceWithOneImplementation::class.java)
            assert(service).isA<A> {  }
        }
    }

    given("more than one service") {
        it("throws an IllegalStateException") {
            expect {
                SingleServiceLoader.load(InterfaceWithTwoImplementation::class.java)
            }.toThrow<IllegalStateException> {
                message {
                    this contains Values("Found more than one implementation ", A1::class.java.name, A2::class.java.name)
                }
            }
        }
    }
})

interface InterfaceWithOneImplementation
class A: InterfaceWithOneImplementation

interface InterfaceWithTwoImplementation
class A1: InterfaceWithTwoImplementation
class A2: InterfaceWithTwoImplementation
