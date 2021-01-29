//
// Copyright (c) ZeroC, Inc. All rights reserved.
//

#pragma once

["java:package:com.zeroc.demos.IceStorm.allSensors2ContextManager"]
module Demo
{
    interface AllSensors2ContextManager
    {
        void tick1(string aqi);
        void tick2(string temperature);
        void tick3(string weather);
        void tick4(string weather);
    }
}
