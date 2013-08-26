# Pacer::Parallel

Parallelize [Pacer](https://github.com/pangloss/pacer) Routes.

## Installation

Add this line to your application's Gemfile:

    gem 'pacer-parallel'

And then execute:

    $ bundle

Or install it yourself as:

    $ gem install pacer-parallel

## Usage

```ruby
  g.v.parallel(threads: 8, in_buffer: 4, out_buffer: 10) do |v|
    v.all(&:out)
  end
```

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request


## Notes


* eagerly consume (1) input and push into a channel
  * ChannelCapPipe
  * create a cap pipe that does this. The pipe's output is the channel
  * source data may be slow. Should probably not use a go block
  * 1 thread in a loop
* Control the construction of parallel pipes. Default 2 threads, make
it configurable.
  * standard copy split pipe can push the channel to subchannels
  * each parallel route pulls from the channel.
    * in a go block (waits will not block go thread pool)
    * ChannelReaderPipe
    * PathChannelReaderPipe
  * parallel routes are unmodified
  * cap each route - eagerly consume input and push into a channel
    * ChannelCapPipe again
  * ExhaustMergePipe + GatherPipe to create a route to an list of
    channels
  * use alts to read from any of the channels
    * ChannelFanInPipe



## Pipe structure built to create parallel route:

 CCP
 CSP (parallelism is 1 thread per pipe being split into)
   CRP -> Work ... -> CCP
   CRP -> Work ... -> CCP
   ...
 EMP
 GP
 CARP
