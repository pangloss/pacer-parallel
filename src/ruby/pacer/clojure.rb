module Pacer
  module Clojure
    import clojure.lang.RT
    import clojure.lang.Symbol
    REQUIRE = RT.var("clojure.core", "require");

    class << self
      def require(namespace)
        REQUIRE.invoke(Symbol.intern(nil, namespace));
      end
    end
  end
end
