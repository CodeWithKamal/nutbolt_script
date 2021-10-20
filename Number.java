public class Number {
    public double value;
    public Position pos_start = null;
    public Position pos_end = null;
    public Context context;
    
    public Number(double value) {
        this.value = value;
        this.set_pos(null, null);
        this.set_context(null);
    }

    public Number set_pos(Position pos_start, Position pos_end) {
        this.pos_start = pos_start;
        this.pos_end = pos_end;
        return this;
    }

    public Number set_context(Context context) {
        this.context = context;
        return this;
    }

    public Object added_to(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number(this.value + ((Number)other).value).set_context(this.context), null);
        }
        return null;
    }

    public Object subbed_by(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number(this.value - ((Number)other).value).set_context(this.context), null);
        }
        return null;
    }

    public Object multed_by(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number(this.value * ((Number)other).value).set_context(this.context), null);
        }
        return null;
    }

    public Object dived_by(Object other) {
        if (other instanceof Number) {
            if (((Number)other).value == 0.0) {
                return new RunTimeResult(null, new RunTimeError(((Number)other).pos_start, ((Number)other).pos_end, "Division by zero", this.context));
            } else {
                return new RunTimeResult((Object)new Number(this.value / ((Number)other).value).set_context(this.context), null);
            }
        }
        return null;
    }

    public Object powed_by(Object other) {
        if (other instanceof Number) {
            if (((Number)other).value == 0.0) {
                return new RunTimeResult(null, new RunTimeError(((Number)other).pos_start, ((Number)other).pos_end, "Division by zero", this.context));
            } else {
                return new RunTimeResult((Object)new Number(Math.pow(this.value, ((Number)other).value)).set_context(this.context), null);
            }
        }
        return null;
    }

    public Object get_comparison_eq(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number((this.value == ((Number)other).value) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }
    
    public Object get_comparison_ne(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number((this.value != ((Number)other).value) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }

    public Object get_comparison_lt(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number((this.value < ((Number)other).value) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }

    public Object get_comparison_gt(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number((this.value > ((Number)other).value) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }

    public Object get_comparison_lte(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number((this.value <= ((Number)other).value) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }

    public Object get_comparison_gte(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number((this.value >= ((Number)other).value) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }

    public Object anded_by(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number(((this.value == 1.0 ? true : false) && (((Number)other).value == 1.0 ? true : false)) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }
    
    public Object ored_by(Object other) {
        if (other instanceof Number) {
            return new RunTimeResult((Object)new Number(((this.value == 1.0 ? true : false) || (((Number)other).value == 1.0 ? true : false)) ? 1.0 : 0.0).set_context(this.context), null);
        }
        return null;
    }

    public Object notted() {
        return new RunTimeResult((Object)new Number((this.value == 0.0) ? 1.0 : 0.0).set_context(this.context), null);
    }

    public Number copy() {
        Number copy = new Number(this.value);
        copy.set_pos(this.pos_start, this.pos_end);
        copy.set_context(this.context);
        return copy;
    }

    public Boolean is_true() {
        return this.value != 0.0;
    }

    public String toString()
    {
        return String.valueOf(this.value);
    }
}
