package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(
    value = "vector3i",
    path = "types/vector3i"
)
@Description("A three-dimensional integer based vector representation")
public class Vector3i {
    public int x;
    public int y;
    public int z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i() {
    }
}
