
def  test(): integer {

    for i << 5 to 1 loop {
        integer p << 2;
        float x << 2;
        for j << 2 to 101 loop {
            float x << 2;
            for z << 3 to 102 loop {
                float x << 2;
                while p < 2 loop {
                    p << p + 1;
                }
            }
        }
    }

}

start: def main2 (integer a | out float c): integer {
    test();
}
