// Step 1: configuring camel
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.impl.DefaultCamelContext

@Grab(group="org.apache.camel",module="camel-core",version="2.15.2")
@Grab(group="org.apache.camel",module="camel-groovy",version="2.15.2")

class SampleBuilder extends RouteBuilder {
	void configure() {
		from("file:in")
		.to("file:out")
	}
}

// step 2: running camel
def camelContext = new DefaultCamelContext()
camelContext.addRoutes(new SampleBuilder())

camelContext.start()

//step 3: sending data across Camel
def fileName = "testFile${new Date().format('yyyy-MM-dd-hh-mm-ss')}.txt"
new File('in', fileName) << "Hello, World!!"

println "Waiting for the return key to proceed..."
System.console().readLine()
// step 4: checking the transfer occurred successfully
def f = new File('out', fileName)
assert f.exists()
assert f.text == "Hello, World!!"

// step 5: stopping camel. The transmission already happened
camelContext.stop()
