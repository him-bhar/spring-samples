spring-context-loaders
======================

h2.Ways to load context with different set of properties

h3.Having multiple config files

In this way we will have multiple config files with same properties, and each time we load context we will use different config file. This way we will have same context loaded with different set of properties. Benefit clean setup of configuration and xmls, no hacks required.

h3.Having single config file which contains configs required to load contexts with different properties

In this way we will have a single config file with same properties having a prefix to identify which context will it belong to, and each time we load context we will use these prefixed property, here the bean definition xml will be a common one, but we will extend PropertyPlaceholderConfigurer class of Spring and add this behavior to append the context prefix .  This way we will have same context loaded with different set of properties. Benefit single configuration file and xmls.
